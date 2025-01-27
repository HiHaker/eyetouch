package com.yonyou.ccomments.controller;
import com.yonyou.ccomments.po.Ccomments;
import com.yonyou.ccomments.dto.CcommentsDTO;
import com.yonyou.ccomments.service.CcommentsService;
import com.yonyou.ccomments.dto.SimpleSearchDTO;
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
* 说明：商品评论基础Controller——提供数据增(CREATE)、删(DELETE、改(UPDATE)、查(READ)等rest接口
* @author  
* @date 2019-10-2 20:20:07
*/
@CrossOrigin
@RestController("com.yonyou.ccomments.controller.CcommentsController")
@RequestMapping(value = "/ccomments/ccomments")
public class CcommentsController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(CcommentsController.class);
    private final static  int PAGE_FLAG_LOAD_ALL = 1;
    private CcommentsService service;

    @Autowired
    public void setCcommentsService(CcommentsService service) {
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
            ,@RequestParam(required = false) String search_content
    ) {
        SimpleSearchDTO searchDTO = new SimpleSearchDTO();
            searchDTO.setSearch_time(search_time);
            searchDTO.setSearch_cid(search_cid);
            searchDTO.setSearch_uid(search_uid);
            searchDTO.setSearch_content(search_content);
        PageRequest pageRequest;
        Sort sort= SearchUtil.getSortFromSortMap(searchDTO.getSorts(),Ccomments.class);
        try {
            if (pageSize== PAGE_FLAG_LOAD_ALL) {
                pageRequest =
                        PageRequest.of(pageIndex,Integer.MAX_VALUE-1,sort);
            }else{
                pageRequest=PageRequest.of(pageIndex,pageSize,sort);
            }
            Page<Ccomments> page = this.service.selectAllByPage(pageRequest, searchDTO.toSearchParams(Ccomments.class) );
            List<CcommentsDTO> dtoList = new ArrayList<>();
            for (Ccomments po:page.getContent()){
                CcommentsDTO dto = new CcommentsDTO();
                BeanUtils.copyProperties(po,dto);
                dtoList.add(dto);
            }
            Page<CcommentsDTO> dtoPage = new UcfPage<>(dtoList,page.getPageable(),page.getTotalElements());
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
        GenericAssoVo<Ccomments> vo = service.getAssoVo( id);
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
    public Object  saveAssoVo(@RequestBody GenericAssoVo<Ccomments> vo){
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
    public Object  deleAssoVo(@RequestBody Ccomments... entities){
        if (entities.length==0){
            return this.buildGlobalError("deleting entity must not be empty");
        }
        Associative annotation = entities[0].getClass().getAnnotation(Associative.class);
        if (annotation != null && !StringUtils.isEmpty(annotation.fkName())) {
            int result =0;
            for (Ccomments entity:entities){
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
     * 根据用户id删除其全部评论
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
     * 根据商品id删除其全部评论
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
     * 根据商品id和用户id删除其评论记录
     * @param commodity_ID
     */
    @RequestMapping(value = "/deleteByCommodityIdAndUserId", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteByCommodityIdAndUserId(
            @RequestParam(required = false) String commodity_ID, String user_ID
    ){
        service.deleteByCommodityIdAndUserId(commodity_ID, user_ID);
    }

    /**
     * 根据商品id查询全部评论记录
     * @param commodity_ID
     * @return
     */
    @RequestMapping(value = "/getAllCommentsByCommodityId", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllCommentsByCommodityId(
            @RequestParam(required = false) String commodity_ID
    ){
        return this.buildSuccess(service.getAllCommentsByCommodityId(commodity_ID));
    }

    /**
     * 根据用户id查询其全部评论记录
     * @param user_ID
     * @return
     */
    @RequestMapping(value = "/getAllCommentsByUserId", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllCommentsByUserId(
            @RequestParam(required = false) String user_ID
    ){
        return this.buildSuccess(service.getAllCommentsByUserId(user_ID));
    }

    /**
    * 单条添加
    * @param entity 业务实体
    * @return 标准JsonResponse结构
    */
    @RequestMapping(value = "/insertSelective", method = {RequestMethod.POST,RequestMethod.PATCH})
    @ResponseBody
    public Object insertSelective(@RequestBody Ccomments entity) {
            entity = this.service.save(entity,true,true);
            CcommentsDTO dto = new CcommentsDTO();
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
    public Object updateSelective(@RequestBody Ccomments entity) {
                        entity = this.service.save(entity,false,true);
            CcommentsDTO dto = new CcommentsDTO();
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
    public Object deleteBatch(@RequestBody List<Ccomments> listData) throws Exception {
        this.service.deleteBatch(listData);
        return super.buildSuccess();
    }
}

