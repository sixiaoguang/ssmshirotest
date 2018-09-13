package com.test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.entity.UserEntity;
import com.test.service.UserService;

@Controller
public class UserController {

	@Resource
	UserService userService;
	
	@RequestMapping("/login")
	public String login(@RequestParam("userName")String userName,@RequestParam("password")String password){
		Subject currentUser=SecurityUtils.getSubject();
		if(!currentUser.isAuthenticated()){
			UsernamePasswordToken token=new UsernamePasswordToken(userName,password);
			try{
				currentUser.login(token);
			}catch(AuthenticationException e){
				System.out.println("AuthenticationException: " +e.getMessage());
			}
		}
		return "redirect:index.jsp";
	}

	@RequestMapping("/userList")
	public String getUserList(Model model){
		List<UserEntity>list=userService.getUserList();
		model.addAttribute("list", list);
		return "forward:list.jsp";
	}
	
	@RequestMapping("/save")
	public void saveUser(UserEntity user,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out=response.getWriter();
			int i=userService.saveUser(user);
			if(i>0){
				out.println("<script>alert('��ӳɹ���');location.href='add.jsp'</script>");
			}else{
				out.println("<script>alert('���ʧ�ܣ�');location.href='add.jsp'</script>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/user")
	public String getUser(@RequestParam("uid")Integer uid,Model model){
		UserEntity user=userService.queryUserById(uid);
		model.addAttribute("user", user);
		return "forward:update.jsp";
	}

	@RequestMapping("/modify")
	public void updateUser(UserEntity user,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		int i=userService.updateUser(user);
		try {
			PrintWriter out=response.getWriter();
			if(i>0){
				out.println("<script>alert('修改成功');location.href='user?uid="+user.getUser_id()+"'</script>");
			}else{
				out.println("<script>alert('修改成功');location.href='user?uid="+user.getUser_id()+"'</script>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/delete")
	public void deleteUser(@RequestParam("uid")Integer uid,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out=response.getWriter();
			boolean flag=userService.deleteUser(uid);
			if(flag){
				out.println("<script>alert('ɾ���ɹ���');location.href='userList'</script>");
			}else{
				out.println("<script>alert('ɾ��ʧ�ܣ�');location.href='userList'</script>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@RequestMapping("/mathError")
	public String mathError(Model model){
		System.out.println("before error");
		int a = 1/0;
		System.out.println("after error");
		List<UserEntity>list=userService.getUserList();
		model.addAttribute("list", list);
		return "forward:list.jsp";
	}
	

	@RequiresPermissions("user:add")
	@RequestMapping("/callAnnotation")
	public String callAnnotation(Model model){
		System.out.println("-------------before callAnnotation");
		Subject subject = SecurityUtils.getSubject();
		System.out.println("用户："+subject.getPrincipal()+"有添加用户权限");
		System.out.println("-------------after callAnnotation");
		return "forward:add.jsp";
	}
	
	
	@RequestMapping("/callMethod")
	public String callMethod(Model model){
		System.out.println("-------------before callMethod");
		Subject subject = SecurityUtils.getSubject();
		subject.checkPermission("user:add");
		System.out.println("用户："+subject.getPrincipal()+"有添加用户权限");		
//		try {
//			subject.checkPermission("user:add");
//			System.out.println("用户："+subject.getPrincipal()+"有添加用户权限");	
//		}catch(UnauthorizedException e) {
//			System.out.println("用户："+subject.getPrincipal()+"没有添加用户权限");	
//		}
		
		System.out.println("-------------after callMethod");
		return "forward:add.jsp";
	}


	@RequestMapping("/callUrl")
	public String callUrl(Model model){
		System.out.println("-------------before callUrl");
		
		Subject subject = SecurityUtils.getSubject();
		System.out.println("用户："+subject.getPrincipal()+"有添加用户权限");
		System.out.println("-------------after callUrl");
		return "forward:add.jsp";
	}
	
	
	
}
