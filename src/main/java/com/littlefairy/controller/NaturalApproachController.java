package com.littlefairy.controller;

import com.gsafety.gemp.common.page.PageResult;
import com.gsafety.gemp.common.result.Result;
import com.gsemergency.natural.approaching.manage.contract.NaturalApproachService;
import com.gsemergency.natural.approaching.manage.contract.dto.in.ApproachInfoConDTO;
import com.gsemergency.natural.approaching.manage.contract.dto.out.ApproachInfoDTO;
import com.gsemergency.natural.approaching.manage.contract.dto.out.ApproachInfoListDTO;
import com.gsemergency.natural.approaching.manage.contract.dto.out.NumberDTO;
import com.gsemergency.natural.approaching.manage.contract.dto.out.ResponsibleInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <B>模块名称：临近预警</B><BR>
 * <B>概要说明：临近预警发布</B><BR>
 *
 * @author zhangql
 * @since 2020/4/3 15:44
 */
@Api(value = "临近预警发布", tags = { "临近预警发布" })
@RestController
@RequestMapping("/api/gsemergency/natural/approaching")
public class NaturalApproachController {


    @ApiOperation(value = "分页查询列表")
    @PostMapping("/list/v1")
    public Result<PageResult<ApproachInfoListDTO>> findByParams(@RequestBody @Valid ApproachInfoConDTO dto, BindingResult bindingResult) {
        return naturalApproachService.getList(dto);
    }

}
