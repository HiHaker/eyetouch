package com.yonyou.myimg.controller;
import com.yonyou.myimg.po.Myimg;
import com.yonyou.myimg.dto.MyimgDTO;
import com.yonyou.myimg.service.MyimgService;
import com.yonyou.myimg.dto.SimpleSearchDTO;
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
* 说明：迁移图片基础Controller——提供数据增(CREATE)、删(DELETE、改(UPDATE)、查(READ)等rest接口
* @author  
* @date 2019-10-5 11:44:39
*/
@CrossOrigin
@RestController("com.yonyou.myimg.controller.MyimgController")
@RequestMapping(value = "/myimg/myimg")
public class MyimgController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(MyimgController.class);
    private final static  int PAGE_FLAG_LOAD_ALL = 1;
    private MyimgService service;

    @Autowired
    public void setMyimgService(MyimgService service) {
        this.service = service;
    }
    /**
    * 分页查询
    * @return 分页集合
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam( defaultValue = "0")Integer pageIndex,@RequestParam( defaultValue = "1000")Integer pageSize
            ,@RequestParam(required = false) String search_course
    ) {
        SimpleSearchDTO searchDTO = new SimpleSearchDTO();
            searchDTO.setSearch_course(search_course);
        PageRequest pageRequest;
        Sort sort= SearchUtil.getSortFromSortMap(searchDTO.getSorts(),Myimg.class);
        try {
            if (pageSize== PAGE_FLAG_LOAD_ALL) {
                pageRequest =
                        PageRequest.of(pageIndex,Integer.MAX_VALUE-1,sort);
            }else{
                pageRequest=PageRequest.of(pageIndex,pageSize,sort);
            }
            Page<Myimg> page = this.service.selectAllByPage(pageRequest, searchDTO.toSearchParams(Myimg.class) );
            List<MyimgDTO> dtoList = new ArrayList<>();
            for (Myimg po:page.getContent()){
                MyimgDTO dto = new MyimgDTO();
                BeanUtils.copyProperties(po,dto);
                dtoList.add(dto);
            }
            Page<MyimgDTO> dtoPage = new UcfPage<>(dtoList,page.getPageable(),page.getTotalElements());
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
        GenericAssoVo<Myimg> vo = service.getAssoVo( id);
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
    public Object  saveAssoVo(@RequestBody GenericAssoVo<Myimg> vo){
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
    public Object  deleAssoVo(@RequestBody Myimg... entities){
        if (entities.length==0){
            return this.buildGlobalError("deleting entity must not be empty");
        }
        Associative annotation = entities[0].getClass().getAnnotation(Associative.class);
        if (annotation != null && !StringUtils.isEmpty(annotation.fkName())) {
            int result =0;
            for (Myimg entity:entities){
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
    * 单条添加
    * @param entity 业务实体
    * @return 标准JsonResponse结构
    */
    @RequestMapping(value = "/insertSelective", method = {RequestMethod.POST,RequestMethod.PATCH})
    @ResponseBody
    public Object insertSelective(@RequestBody Myimg entity) {
            entity = this.service.save(entity,true,true);
            MyimgDTO dto = new MyimgDTO();
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
    public Object updateSelective(@RequestBody Myimg entity) {
                        entity = this.service.save(entity,false,true);
            MyimgDTO dto = new MyimgDTO();
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
    public Object deleteBatch(@RequestBody List<Myimg> listData) throws Exception {
        this.service.deleteBatch(listData);
        return super.buildSuccess();
    }
}

