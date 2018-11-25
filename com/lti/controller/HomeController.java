package com.lti.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lti.dao.TransDao;

import com.lti.model.Bus;
import com.lti.model.BusSeats;
import com.lti.model.Registration;
import com.lti.model.Route;
import com.lti.model.Security;
import com.lti.model.Travellers;

/**
 * This is to mention it is the Controller 
 * @author 10649718
 *
 */
@Controller
public class HomeController {

	@Autowired TransDao edao;
	List<String> sr=new ArrayList<String>();
	//It will mapped while registering as a new User
	@RequestMapping("/signup")
	public ModelAndView Insert(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Registration sp,HttpSession ses ) throws Exception
	{

		try {
			long mobile=sp.getMobile();
			int i=edao.insertData(sp);

			ses.setAttribute("mobile",mobile);}
		catch (Exception e) {

			return new ModelAndView("Exception");
		}
		return new ModelAndView("SecurityQuestionForm","message","welcome");
	}
	/**
	 * It will mapped when you forgotten your password
	 * @param request
	 * @param response
	 * @param sec
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forgotPassword")
	public ModelAndView SearchPassword(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Security sec,HttpSession ses ) throws Exception
	{


		try {
			List<Security> status=edao.SearchPassword(sec);
			long mob=(long)status.get(0).getMobile();
			ses.setAttribute("Mobile", mob);
		}catch(Exception e) {
			response.sendRedirect("Exception.jsp");
		}


		return new ModelAndView("NewPasswordSetUp");



	}

	/**
	 * It is to update your password in setting
	 * @param response
	 * @param request
	 * @param sec
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/security")
	public ModelAndView security(HttpServletResponse response,HttpServletRequest request,@ModelAttribute Security sec,HttpSession ses) throws Exception {


		request.getSession(true);
		long mobile=(long) ses.getAttribute("mobile");


		int i=edao.security(sec, mobile);
		return new ModelAndView("RegistrationSuccess");
	}

	/**
	 * Once the user login it will be mapped here
	 * @param request
	 * @param response
	 * @param s
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/Login")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response,@ModelAttribute Registration s,HttpSession ses) throws Exception
	{

		List<Registration> status;

		try {
			status=edao.Login(s);
			Registration r=(Registration)status.get(0);

			ses.setAttribute("MyProfile", r);


			List<Registration> adminmob=edao.AdminLogin();
			Registration ad=(Registration)adminmob.get(0);
			if(status.size()>0 )
			{

				if(r.getMobile() == ad.getAdminMobile()) {

					return new ModelAndView("AdminHomePage");
				}
				else {
					return new ModelAndView("RedBusHomePage");
				}
			}
		}
		catch(Exception e) {
			response.sendRedirect("Exception.jsp");
		}

		return new ModelAndView("Login");

	}

	/**If you forgot the password it will be mapped here
	 * @param response
	 * @param request
	 * @param forget
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forget")
	public ModelAndView MyProfile(HttpServletResponse response,HttpServletRequest request,@ModelAttribute com.lti.model.UpdatePassword forget,HttpSession ses) throws Exception{
		request.getSession();

		Registration s2=(Registration)ses.getAttribute("MyProfile");
		long mob=s2.getMobile();



		int h=edao.forgetPassword(mob,forget);

		if(h==0) {
			response.sendRedirect("ChangePasswordAlert.jsp");

		}


		return new ModelAndView("RedBusHomePage");
	}

	/**If you forgot the password it will be mapped here for Admin 
	 * @param response
	 * @param request
	 * @param forget
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forgetpass")
	public ModelAndView AdminMyProfile(HttpServletResponse response,HttpServletRequest request,@ModelAttribute com.lti.model.UpdatePassword forget,HttpSession ses) throws Exception{
		request.getSession();
		Registration s2=(Registration)ses.getAttribute("MyProfile");
		long mob=s2.getMobile();



		int h=edao.forgetPassword(mob,forget);
		if(h==0) {
			response.sendRedirect("ChangePasswordAlert.jsp");
		}


		return new ModelAndView("AdminHomePage");
	}

	/**
	 * To add a bus by admin it will be mapped here
	 * @param request
	 * @param response
	 * @param b
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addbus")
	public ModelAndView InsertBus(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Bus b ) throws Exception
	{
		try {
			int i=edao.insertbus(b);

		}catch(Exception e) {
			response.sendRedirect("AdminException.jsp");
		}
		return new ModelAndView("AdminSuccessPage","message","welcome");
	}

	/**
	 * To add a route by admin it will be mapped here
	 * @param request
	 * @param response
	 * @param route
	 * @param b
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addroute")
	public ModelAndView InsertRoute(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Route route,Bus b) throws Exception
	{
		try {

			int i=edao.insertRoute(route);
		}
		catch(Exception e) {
			response.sendRedirect("AdminException.jsp");
		}

		return new ModelAndView("AdminSuccessPage","message","successfully added the bus");
	}

	/**
	 * To view the ticket
	 * @param req
	 * @param res
	 * @param travel
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ViewTicket")
	public ModelAndView ViewTicket(HttpServletRequest req, HttpServletResponse res,@ModelAttribute Travellers travel,HttpSession ses) throws Exception
	{
		long mob=((Registration) ses.getAttribute("MyProfile")).getMobile();
		List lst= edao.searchTravellers(travel,mob);
		if(lst.size()>0) {
			return new ModelAndView("ViewTicketDisplay","msg",lst);
		}
		return new ModelAndView("TicketOopsPage","msg",lst);

	}
	/**
	 * To cancel the ticket
	 * @param req
	 * @param res
	 * @param travel
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/CancelTicket")
	public ModelAndView CancelTicket(HttpServletRequest req, HttpServletResponse res,@ModelAttribute Travellers travel,HttpSession ses) throws Exception
	{
		long mob=((Registration) ses.getAttribute("MyProfile")).getMobile();
		ses.setAttribute("tr", travel);
		List lst= edao.searchTravellers(travel,mob);
		if(lst.size()>0) {
			return new ModelAndView("CancelTicketDisplay","msg",lst);
		}
		return new ModelAndView("TicketOopsPage","msg",lst);

	}


	/**
	 * To confirm the cancellation
	 * @param req
	 * @param res
	 * @param ses
	 * @throws Exception
	 */
	@RequestMapping("/Confirm")
	public void ConfirmCancelTicket(HttpServletRequest req, HttpServletResponse res,HttpSession ses) throws Exception
	{
		try {
			Travellers travel =(Travellers) ses.getAttribute("tr");
			List lst=edao.CheckPNRandMob(travel);
			if(lst.size()>0) {
				int j=edao.CancelTravellers(travel);
				if(j>1) {
					res.sendRedirect("CancelSuccess.jsp");
				}


			}else {
				res.sendRedirect("CancelTicket.jsp");
			}}
		catch(Exception e) {
			res.sendRedirect("TicketOopsPage.jsp");
		}


	}

	/**
	 * To display the passenger details
	 * @param response
	 * @param request
	 * @param bs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/displayall")
	public ModelAndView display(HttpServletResponse response,HttpServletRequest request,@ModelAttribute BusSeats bs) throws Exception{

		List lst=edao.DisplayAll(bs);
		return new ModelAndView("PassengerDetailsDisplay","message",lst);

	}

	/**
	 * To get the user details
	 * @param response
	 * @param request
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/MyProfileRetrive")
	public ModelAndView MyProfile(HttpServletResponse response,HttpServletRequest request,HttpSession ses) throws Exception{

		Registration sp=(Registration)ses.getAttribute("MyProfile");
		long mobile=sp.getMobile();
		List lst= edao.myProfileDetails(mobile);
		List<Registration> adminmob=edao.AdminLogin();
		Registration ad=(Registration)adminmob.get(0);
		if(ad.getAdminMobile() == mobile) {
			return new ModelAndView("AdminMyProfile","msg",lst);
		}
		return new ModelAndView("MyProfile","msg",lst);

	}

	/**
	 * To logout
	 * @param response
	 * @param request
	 * @param ses
	 * @throws Exception
	 */

	@RequestMapping("/Logout")
	public void LogOut(HttpServletResponse response,HttpServletRequest request,HttpSession ses) throws Exception{

		ses.invalidate();
		response.sendRedirect("RedBusHome.jsp");


	}

	/**
	 * To change password
	 * @param request
	 * @param response
	 * @param sec
	 * @param u
	 * @param ses
	 * @throws Exception
	 */
	@RequestMapping("/UpdatePassword")
	public void UpdatePassword(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Security sec,com.lti.model.UpdatePassword u,HttpSession ses ) throws Exception
	{
		long mobile=(Long)ses.getAttribute("Mobile");
		int i=edao.Update(mobile,u);
		if(i>0) {
			response.sendRedirect("Login.jsp");
		}
		else
		{
			response.sendRedirect("ChangePasswordAlert.jsp");
		}

	}



	/**
	 * Searching the bus from home page
	 * @param req
	 * @param res
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/SearchBus")
	public ModelAndView SearchBus(HttpServletRequest req, HttpServletResponse res,HttpSession ses) throws Exception
	{
		try {
			int j=0;
			String month[]= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
			String source=req.getParameter("source");
			String destination=req.getParameter("destination");

			String[] str=req.getParameter("dat").split("-");
			for (int i = 0; i < month.length; i++) {
				if(month[i].equalsIgnoreCase(str[1]))
					j=i+1;
			}
			LocalDate dat=LocalDate.of(Integer.parseInt(str[2]), j, Integer.parseInt(str[0]));
			sr.add(source);
			sr.add(destination);
			DayOfWeek day=dat.getDayOfWeek();
			String day1=day.toString();
			sr.add(day1);

			ses.setAttribute("FareCal", sr);
			Map<Bus, Route> map=edao.searchBus(source,destination,day);
			if(map.size()>0) {

				return new ModelAndView("DisplaySearchedCheck","msg",map);
			}
		}
		catch(Exception e) {
			res.sendRedirect("OopsPage.jsp");
		}

		return new ModelAndView("OopsPage");
	}


	/**
	 * To send the bus number selected from one page to another page
	 * @param request
	 * @param response
	 * @param ses
	 * @throws Exception
	 */
	@RequestMapping("/hi")
	public void UpdatePassword(HttpServletRequest request,HttpServletResponse response,HttpSession ses ) throws Exception
	{
		ses.setAttribute("asd", request.getParameter("asd"));
		response.sendRedirect("BusSeatLayout.jsp");
	}

	/**
	 * To Book the ticket
	 * @param req
	 * @param res
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/Book")
	public ModelAndView BookTicket(HttpServletRequest req, HttpServletResponse res,HttpSession ses) throws Exception
	{
		try {
			Registration r=(Registration)ses.getAttribute("MyProfile");
			List<Registration> reglist=edao.RegistrationDetails(r);
			Registration reg=(Registration)reglist.get(0);
			String str[]=req.getParameterValues("seatno");
			int copass=str.length-1;
			String s=(String) ses.getAttribute("asd");
			int busno=Integer.parseInt(s);
			List<String> s1=(List<String>)ses.getAttribute("FareCal");
			String sou=s1.get(0);
			String des=s1.get(1);
			String day=s1.get(2);
			double fare=edao.fareGet(busno,sou,des);
			edao.bookTicket(str,reg,copass,fare,busno,day);}
		catch(Exception e) {
			res.sendRedirect("TicketException.jsp");
		}
		return new ModelAndView("TicketBooked","msg","yes");

	}

	/**
	 * To view my ticket
	 * @param req
	 * @param res
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ViewMyTicket")
	public ModelAndView ViewBookedTicket (HttpServletRequest req, HttpServletResponse res,HttpSession ses) throws Exception{
		List lst=null;
		Registration r=(Registration)ses.getAttribute("MyProfile");
		long mob=r.getMobile();
		try {
		 lst=edao.ViewBookedTravellers(mob);}
		catch(Exception e) {
			res.sendRedirect("OopsPage.jsp");
		}
		return new ModelAndView("ViewMyBookedTicket","msg",lst);
	}
}
