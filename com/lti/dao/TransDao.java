package com.lti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.lti.model.Bus;
import com.lti.model.BusSeats;
import com.lti.model.Registration;
import com.lti.model.Route;
import com.lti.model.Security;
import com.lti.model.Travellers;
import com.lti.model.UpdatePassword;
public class TransDao {
	List<BusSeats> list;
	List<Bus> l1;
	List<Route> l2;
	LinkedHashMap<Bus, Route> map=new LinkedHashMap<Bus,Route>();
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * This is to insert the user record into DataBase for signup member
	 * @param sp
	 * @return
	 */
	public int insertData(Registration sp) {

		String query="insert into signup values"+"('"+sp.getName()+"','"+sp.getGender()+"','"+sp.getMobile()+"','"+sp.getEmail()+"','"+sp.getAge()+"','"+sp.getPassword()+"')";

		return jdbcTemplate.update(query);
	}

	/**
	 * It will check whether user  is registered or not
	 * @param s
	 * @return
	 */
	public List<Registration> Login(Registration s){  
		return jdbcTemplate.query("select mobile,password from signup where mobile = "+s.getMobile()+" and "+"password = '"+s.getLoginPassword()+"'",new ResultSetExtractor<List<Registration>>(){  
			public List<Registration> extractData(ResultSet rs) throws SQLException,  
			DataAccessException {  
				List<Registration> list=new ArrayList<Registration>();  
				while(rs.next()){  
					Registration sp=new Registration();
					sp.setMobile(rs.getLong(1));
					list.add(sp);
				}  
				return list;  
			}  
		});  
	}
	/**
	 * To check whether it is Admin user or not
	 * @return
	 */
	public List<Registration> AdminLogin(){  
		return jdbcTemplate.query("select mobile from signup where name='admin' ",new ResultSetExtractor<List<Registration>>(){  
			public List<Registration> extractData(ResultSet rs) throws SQLException,  
			DataAccessException {  

				List<Registration> list=new ArrayList<Registration>();  
				while(rs.next()){  

					Registration sp=new Registration();

					sp.setAdminMobile(rs.getLong(1));

					list.add(sp);

				}  
				return list;  
			}  
		});  
	}

	/**
	 * To insert busses
	 * @param b
	 * @return
	 */
	public int insertbus(Bus b) {
		String query="insert into bus values"+"('"+b.getBusno()+"','"+b.getBustype()+"','"+b.getAc()+"')";
		return jdbcTemplate.update(query);
	}

	/**
	 * To insert routes
	 * @param route
	 * @return
	 */
	public int insertRoute(Route route) {

		String query="insert into route values"+"('"+route.getBusno()+"','"+route.getSource()+"','"+route.getDestination()+"','"+route.getDepttime()+"','"+route.getArrtime()+"','"+route.getFare()+"','"+route.getDay()+"')";
		return jdbcTemplate.update(query);

	}

	/**
	 * It helps to Re-set the Password
	 * @param mob
	 * @param forget
	 * @return
	 */
	public int forgetPassword(long mob,UpdatePassword forget) {



		String str="update signup set password= '"+forget.getNewPassword()+"'where password='"+forget.getOldPassword()+"'and mobile= "+mob+" ";

		int h1=jdbcTemplate.update(str);
		return h1;

	}

	/**
	 * To select all the details of the travellers
	 * @param trv1
	 * @param mob
	 * @return
	 */
	public List searchTravellers(Travellers trv1, long mob)
	{

		int id=trv1.getPNRNo();

		List l=jdbcTemplate.queryForList("select trv.Name,trv.Age,trv.Gender,trv.CoPass,trv.TotalFare,seat.SeatNo,seat.BusNo,route.Source,route.Destination,route.Fare,route.ArrTime,route.DeptTime from Travellers trv,BusSeats seat,Route route where trv.PNRNo=seat.PNRNo and route.BusNo=seat.BusNo and route.day=seat.day and trv.PNRNo='"+id+"' and trv.mobile= "+mob);
		return l;

	}

	/**
	 * To get pnrno and confirm cancellation of ticket
	 * @param travel
	 * @return
	 */
	public List CheckPNRandMob(Travellers travel) {
		String query="select mobile,pnrno from travellers where pnrno='"+travel.getPNRNo()+"'";
		List lst=jdbcTemplate.queryForList(query);
		return  lst;
	}

	/**
	 * To cancel the tickets
	 * @param tr
	 * @return
	 */
	public int CancelTravellers(Travellers tr)
	{

		String query="update busseats b set b.pnrno=0 where b.day IN (Select day from route) and b.busno IN (Select busno from route) and pnrno="+tr.getPNRNo(); 
		String query1="update travellers set status ='Cancelled' where pnrno="+tr.getPNRNo();
		int i=jdbcTemplate.update(query);
		int j=jdbcTemplate.update(query1);
		return i+j;
	}

	/**
	 * To give acknowledgement to the user that the ticket is booked
	 * @param mob
	 * @return
	 */
	public List ViewBookedTravellers(long mob)
	{


		List l=jdbcTemplate.queryForList("select trv.pnrno,trv.Name,trv.Age,trv.Gender,trv.CoPass,trv.TotalFare,seat.SeatNo,seat.BusNo,route.Source,route.Destination,route.Fare,route.ArrTime,route.DeptTime from Travellers trv,BusSeats seat,Route route where trv.PNRNo=seat.PNRNo and route.BusNo=seat.BusNo and route.day=seat.day and trv.mobile=" +mob+ " ORDER BY trv.pnrno ");

		return l;

	}

	/**
	 * To display all passengers of a particular bus
	 * @param bs
	 * @return
	 */
	public List DisplayAll(BusSeats bs)
	{
		int id=bs.getBusno();
		List lst= jdbcTemplate.queryForList("select bu.seatno,bu.pnrno,trv.name,trv.age,trv.gender,trv.mobile,trv.copass from busseats bu,travellers trv where trv.pnrno=bu.pnrno and trv.status='booked' \r\n" + 
				" and bu.busno= "+bs.getBusno());
		return lst;

	}

	/**
	 * To get user's profile details
	 * @param mobile
	 * @return
	 */
	public List myProfileDetails(long mobile) {
		List lst=jdbcTemplate.queryForList("select * from signup s where s.mobile= "+mobile);
		return lst;
	}

	/**
	 * To set the users' security answers
	 * @param sec
	 * @param mob
	 * @return
	 */
	public int security(Security sec,long mob) {
		String query="insert into security values("+mob+",'"+sec.getFriend()+"','"+sec.getMovie()+"','"+sec.getFather_year()+"')";
		return jdbcTemplate.update(query);
	}

	/**
	 * It will help to set a new password once
	 * @param sec
	 * @return
	 */
	public List<Security> SearchPassword(Security sec)
	{
		return jdbcTemplate.query("select mobile from security where friend = '"+sec.getFriend()+"' and "+"movie = '"+sec.getMovie()+"' and  "+"father_year = '"+sec.getFather_year()+"'",new ResultSetExtractor<List<Security>>(){
			public List<Security> extractData(ResultSet rs) throws SQLException,  
			DataAccessException {  

				List<Security> list=new ArrayList<Security>();  


				while(rs.next()){  
					Security sp=new Security();
					sp.setMobile(rs.getLong(1));

					list.add(sp);

				}  
				return list;  
			}  
		});  
	}

	/**
	 * To change password
	 * @param mob
	 * @param u
	 * @return
	 */
	public int Update(long mob,UpdatePassword u) {
		String str="update signup set password= '"+u.getNewPassword()+"'where mobile= "+mob+" ";
		int i=jdbcTemplate.update(str);
		return i;
	}

	/**
	 * Search The Bus
	 * @param source
	 * @param destination
	 * @param day
	 * @return
	 */
	public Map<Bus, Route> searchBus(String source,String destination,DayOfWeek day)
	{
		l1=jdbcTemplate.query("select * from Bus", new BeanPropertyRowMapper(Bus.class));
		l2=jdbcTemplate.query("select * from Route where day='"+day+"' and source='"+source+"' and destination='"+destination+"'", new BeanPropertyRowMapper(Route.class));
		for(int i=0;i<l1.size();i++)
			for (int j = 0; j < l2.size(); j++) {
				if(l1.get(i).getBusno()== (l2.get(j).getBusno()))
					map.put(l1.get(i), l2.get(j));

			}
		return map;
	}

	/**
	 * To get registered users
	 * @param r
	 * @return
	 */
	public List<Registration> RegistrationDetails(Registration r)
	{
		return jdbcTemplate.query("select * from SignUp where mobile= "+r.getMobile(),new BeanPropertyRowMapper(Registration.class));

	}

	/**
	 * To get the fare of the routes
	 * @param busno
	 * @param sou
	 * @param des
	 * @return
	 */
	public double fareGet(int busno,String sou,String des) {
		String query="select fare from route where source= '"+sou+"' and destination= '"+des+"' and busno= "+busno;
		return jdbcTemplate.queryForObject(query, Double.class);
	}

	/**
	 * To book ticket
	 * @param str
	 * @param r
	 * @param copass
	 * @param fare
	 * @param busno
	 * @param day
	 */
	public void bookTicket(String[] str,Registration r,int copass,double fare,int busno,String day)
	{
		double farefound;
		if(copass >0) {
			farefound=(copass+1)*fare;
		}else {
			farefound=fare;
		}
		jdbcTemplate.update("insert into travellers values(seq.nextval,'"+r.getName()+"','"+r.getGender()+"',"+r.getMobile()+",'"+r.getEmail()+"',"+r.getAge()+",'booked',"+copass+","+farefound+")");

		List<Integer> pnr = null;
		int[] pnrno=new int[24];

		jdbcTemplate.query("select seatno,pnrno from busseats where busno="+busno+" and day='"+day+"'", new ResultSetExtractor<List<BusSeats>>() {

			@Override
			public List<BusSeats> extractData(ResultSet rs) throws SQLException, DataAccessException {
				list=new ArrayList<BusSeats>();  
				while(rs.next()){  
					BusSeats e=new BusSeats();  
					e.setSeatno(rs.getString(1));  
					e.setPnrno(rs.getInt(2));

					list.add(e);

				}
				return list;
			}
		});
		for(int i=0;i<list.size();i++) {
			for(int j=0;j<str.length;j++)
				if((list.get(i).getPnrno()==0)&&(list.get(i).getSeatno().equals(str[j]))) {
					jdbcTemplate.query("select pnrno from travellers where mobile= "+r.getMobile() ,new ResultSetExtractor<List >() {
						public List extractData(ResultSet rs) throws SQLException, DataAccessException {

							int i=0;
							while(rs.next()){  
								pnrno[i]=rs.getInt(1);
								++i;
							}
							return pnr;
						}
					});   

					

				}
		}
		for (int j2 = 0; j2 < str.length; j2++) {
			
									jdbcTemplate.update("update busseats set pnrno= "+pnrno[0]+" where busno="+busno+" and seatno="+"'"+str[j2]+"' and day='"+day+"'");
								}
	}
}
