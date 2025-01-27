package com.yonyou.clikes.controller;
import com.yonyou.clikes.po.Clikes;
import com.yonyou.clikes.dto.ClikesDTO;
import com.yonyou.clikes.service.ClikesService;
import com.yonyou.clikes.dto.SimpleSearchDTO;
import com.yonyou.iuap.baseservice.service.util.SearchUtil;
import com.yonyou.iuap.baseservice.entity.annotation.Associative;
import com.yonyou.iuap.baseservice.vo.GenericAssoVo;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.ucf.dao.support.UcfPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
/**
* 说明：商品点赞基础Controller——提供数据增(CREATE)、删(DELETE、改(UPDATE)、查(READ)等rest接口
* @author  
* @date 2019-10-2 17:53:56
*/
@CrossOrigin
@RestController("com.yonyou.clikes.controller.ClikesController")
@RequestMapping(value = "/clikes/clikes")
public class ClikesController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(ClikesController.class);
    private final static  int PAGE_FLAG_LOAD_ALL = 1;
    private ClikesService service;

    @Autowired
    public void setClikesService(ClikesService service) {
        this.service = service;
    }
    /**
    * 分页查询
    * @return 分页集合
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam( defaultValue = "0")Integer pageIndex,@RequestParam( defaultValue = "1000")Integer pageSize
            ,@RequestParam(required = false) String search_time
            ,@RequestParam(required = false) String search_cid
            ,@RequestParam(required = false) String search_uid
    ) {
        SimpleSearchDTO searchDTO = new SimpleSearchDTO();
            searchDTO.setSearch_time(search_time);
            searchDTO.setSearch_cid(search_cid);
            searchDTO.setSearch_uid(search_uid);
        PageRequest pageRequest;
        Sort sort= SearchUtil.getSortFromSortMap(searchDTO.getSorts(),Clikes.class);
        try {
            if (pageSize== PAGE_FLAG_LOAD_ALL) {
                pageRequest =
                        PageRequest.of(pageIndex,Integer.MAX_VALUE-1,sort);
            }else{
                pageRequest=PageRequest.of(pageIndex,pageSize,sort);
            }
            Page<Clikes> page = this.service.selectAllByPage(pageRequest, searchDTO.toSearchParams(Clikes.class) );
            List<ClikesDTO> dtoList = new ArrayList<>();
            for (Clikes po:page.getContent()){
                ClikesDTO dto = new ClikesDTO();
                BeanUtils.copyProperties(po,dto);
                dtoList.add(dto);
            }
            Page<ClikesDTO> dtoPage = new UcfPage<>(dtoList,page.getPageable(),page.getTotalElements());
            return this.buildSuccess(dtoPage);
        } catch (Exception exp) {
            logger.error("exp", exp);
            return this.buildError("msg", "Error query database", RequestStatusEnum.FAIL_FIELD);
        }
    }


     /**
     * 主子表合并处理--主表单条查询
     * @return GenericAssoVo ,entity中保存的是单条主表数据,sublist中保存的是字表数据,一次性全部加载
     */
    @RequestMapping(value = "/getAssoVo" , method = RequestMethod.GET)
    @ResponseBody
    public Object  getAssoVo(@RequestParam(value = "id")   Serializable  id){
        if (null==id){ return buildSuccess();}
        GenericAssoVo<Clikes> vo = service.getAssoVo( id);
        JsonResponse result = this.buildSuccess("entity",vo.getEntity());//保证入参出参结构一致
        result.getDetailMsg().putAll(vo.getSublist());
        return  result;
    }
    /**
     * 主子表合并处理--主表单条保存
     * @param vo GenericAssoVo ,entity中保存的是单条主表数据,sublist中保存的是子表数据
     * @return 主表的业务实体
     */
    @RequestMapping(value = "/saveAssoVo", method = RequestMethod.POST)
    @ResponseBody
    public Object  saveAssoVo(@RequestBody GenericAssoVo<Clikes> vo){
        Associative annotation= vo.getEntity().getClass().getAnnotation(Associative.class);
        if (annotation == null || StringUtils.isEmpty(annotation.fkName())) {
            return buildError("", "No entity got @Associative nor fkName", RequestStatusEnum.FAIL_FIELD);
        }
        Object result =service.saveAssoVo(vo);
        return this.buildSuccess(result) ;
    }

    /**
     * 主子表合并处理--主表单条删除,若取消级联删除请在主实体上注解改为@Associative(fkName = "NA",deleteCascade = false)
     * @param entities 待删除业务实体
     * @return 删除成功的实体
     */
    @RequestMapping(value = "/deleAssoVo", method = RequestMethod.POST)
    @ResponseBody
    public Object  deleAssoVo(@RequestBody Clikes... entities){
        if (entities.length==0){
            return this.buildGlobalError("deleting entity must not be empty");
        }
        Associative annotation = entities[0].getClass().getAnnotation(Associative.class);
        if (annotation != null && !StringUtils.isEmpty(annotation.fkName())) {
            int result =0;
            for (Clikes entity:entities){
                if (StringUtils.isEmpty(entity.getId())) {
                    return this.buildError("ID", "ID field is empty:"+entity.toString(), RequestStatusEnum.FAIL_FIELD);
                } else {
                    result += this.service.deleAssoVo(entity);
                }
            }
            return this.buildSuccess(result + " rows(entity and its subEntities) has been deleted!");
        } else {
            return this.buildError("", "Nothing got @Associative nor fkName", RequestStatusEnum.FAIL_FIELD);
        }
    }

    /**
     * 根据用户的id删除其全部点赞记录
     * @param user_ID
     */
    @RequestMapping(value = "/deleteByUserId", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteByUserId(
            @RequestParam(required = false) String user_ID
    ){
        service.deleteByUserId(user_ID);
    }

    /**
     * 根据商品的id删除其全部点赞记录
     * @param commodity_ID
     */
    @RequestMapping(value = "/deleteByCommodityId", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteByCommodityId(
            @RequestParam(required = false) String commodity_ID
    ){
        service.deleteByCommodityId(commodity_ID);
    }

    /**
     * 根据用户的id和商品的id删除其点赞记录
     * @param commodity_ID
     */
    @RequestMapping(value = "/deleteByUserIdAndCommodityId", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteByUserIdAndCommodityId(
            @RequestParam(required = false) String user_ID, String commodity_ID
    ){
        service.deleteByUserIdAndCommodityId(user_ID, commodity_ID);
    }

    /**
     * 得到某商品的全部点赞用户
     * @param commodity_ID
     * @return
     */
    @RequestMapping(value = "/getAllUsersByCommodityId", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllUsersByCommodityId(
            @RequestParam(required = false) String commodity_ID
    ){
        return this.buildSuccess(service.getAllUsersByCommodityId(commodity_ID));
    }

    /**
     * 得到某用户点赞的全部商品
     * @param user_ID
     * @return
     */
    @RequestMapping(value = "/getAllCommodityByUserId", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllCommodityByUserId(
            @RequestParam(required = false) String user_ID
    ){
        return this.buildSuccess(service.getAllCommodityByUserId(user_ID));
    }

    /**
     * 查询某条点赞记录
     * @param user_ID
     * @return
     */
    @RequestMapping(value = "/getByUserIdAndCommodityId", method = RequestMethod.GET)
    @ResponseBody
    public Object getByUserIdAndCommodityId(
            @RequestParam(required = false) String user_ID, String commodity_ID
    ){
        return this.buildSuccess(service.getByUserIdAndCommodityId(user_ID,commodity_ID));
    }

    /**
    * 单条添加
    * @param entity 业务实体
    * @return 标准JsonResponse结构
    */
    @RequestMapping(value = "/insertSelective", method = {RequestMethod.POST,RequestMethod.PATCH})
    @ResponseBody
    public Object insertSelective(@RequestBody Clikes entity) {
            entity = this.service.save(entity,true,true);
            ClikesDTO dto = new ClikesDTO();
        try {
            BeanUtils.copyProperties(entity,dto);
            return this.buildSuccess(dto);
        }catch(Exception exp) {
            return this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
        }
    }


    /**
    * 单条修改
    * @param entity 业务实体
    * @return 标准JsonResponse结构
    */
    @RequestMapping(value = "/updateSelective", method = {RequestMethod.POST,RequestMethod.PATCH})
    @ResponseBody
    public Object updateSelective(@RequestBody Clikes entity) {
                        entity = this.service.save(entity,false,true);
            ClikesDTO dto = new ClikesDTO();
        try {
            BeanUtils.copyProperties(entity,dto);
            return this.buildSuccess(dto);
        }catch(Exception exp) {
            return this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
        }
    }


    /**
    * 批量删除
    * @param listData 业务实体列表
    * @return 标准JsonResponse结构
    * @throws Exception
    */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBatch(@RequestBody List<Clikes> listData) throws Exception {
        this.service.deleteBatch(listData);
        return super.buildSuccess();
    }
}

