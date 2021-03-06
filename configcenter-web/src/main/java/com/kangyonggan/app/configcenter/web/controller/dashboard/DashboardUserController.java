package com.kangyonggan.app.configcenter.web.controller.dashboard;

import com.kangyonggan.app.configcenter.biz.service.UserService;
import com.kangyonggan.app.configcenter.model.vo.ShiroUser;
import com.kangyonggan.app.configcenter.model.vo.User;
import com.kangyonggan.app.configcenter.model.vo.UserProfile;
import com.kangyonggan.app.configcenter.web.controller.BaseController;
import com.kangyonggan.app.configcenter.web.util.FileUpload;
import com.kangyonggan.app.configcenter.web.util.Images;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/20
 */
@Controller
@RequestMapping("dashboard/user")
public class DashboardUserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 基本信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    @RequiresPermissions("USER_PROFILE")
    public String profile(Model model) {
        ShiroUser shiroUser = userService.getShiroUser();
        User user = userService.findUserById(shiroUser.getId());
        UserProfile userProfile = userService.findUserProfileByUsername(shiroUser.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("userProfile", userProfile);
        return getPathRoot() + "/profile";
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> profile(@ModelAttribute(value = "userProfile") @Valid UserProfile userProfile, BindingResult result,
                                       @ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult,
                                       @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws FileUploadException {
        Map<String, Object> resultMap = getResultMap();
        ShiroUser shiroUser = userService.getShiroUser();

        if (!result.hasErrors() && !bindingResult.hasErrors()) {
            if (avatar != null && !avatar.isEmpty()) {
                String fileName = FileUpload.upload(avatar);
                String large = Images.large(fileName);
                userProfile.setLargeAvatar(large);
                String middle = Images.middle(fileName);
                userProfile.setMediumAvatar(middle);
                String small = Images.small(fileName);
                user.setSmallAvatar(small);
            }

            userService.updateUserAndProfile(user, userProfile);
            resultMap.put("user", userService.findUserById(shiroUser.getId()));
            resultMap.put("userProfile", userService.findUserProfileByUsername(shiroUser.getUsername()));
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

}
