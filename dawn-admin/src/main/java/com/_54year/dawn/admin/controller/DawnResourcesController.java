package com._54year.dawn.admin.controller;


import com._54year.dawn.admin.entity.DawnResources;
import com._54year.dawn.admin.service.DawnResourcesService;
import com._54year.dawn.common.annotation.DawnResult;
import com._54year.dawn.common.annotation.RequestUser;
import com._54year.dawn.common.annotation.HasRole;
import com._54year.dawn.common.entity.CurrentUser;
import com._54year.dawn.mysql.config.DawnPage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * dawn-资源表 前端控制器
 * </p>
 *
 * @author Andersen
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/admin/dawnResources")

public class DawnResourcesController {
	@Autowired
	DawnResourcesService dawnResourcesService;

	@PostMapping("/list")
	@DawnResult
	@HasRole("admin")
	public Object list(@RequestBody JSONObject param,@RequestUser CurrentUser currentUser) {
		System.out.println(currentUser);
		return dawnResourcesService.page(new DawnPage<>(param));
	}

	@PostMapping("/save")
	@DawnResult
	@HasRole("admin")
	public Object save(@RequestBody DawnResources dawnResources) {
		return dawnResourcesService.save(dawnResources);
	}

	@PostMapping("/update")
	@DawnResult
	@HasRole("admin")
	public Object update(@RequestBody DawnResources dawnResources) {
		return dawnResourcesService.updateById(dawnResources);
	}

	@PostMapping("/delete")
	@DawnResult
	@HasRole("admin")
	public Object delete(@RequestBody DawnResources dawnResources) {
		return dawnResourcesService.removeById(dawnResources.getId());
	}


}

