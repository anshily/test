package io.anshily.base.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("UtilDao")
@Component
@Transactional(readOnly = false, rollbackFor = DataAccessException.class,propagation= Propagation.REQUIRED)
public class UtilDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 
	 * @方法名：setAppWebServiceLog
	 * @功能描述：[接口操作日志]
	 * @作者：景清华
	 * @parm 接口状态:接口方法:methodName;请求xml:reqXml;反回xml:resposeXml
	 * @日期：2016年1月21日
	 */
	public void setAppWebServiceLog(String methodName,String reqXml,String resposeXml) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String dateFmt=sdf.format(new Date());

		String sql = "INSERT INTO WEBSERVICE_LOG\n" +
						"  (log_id,\n" + 
						"   oper_date,\n" + 
						"   method_name,\n" + 
						"   request_xml,\n" +
						"	response_xml)\n"+
						"VALUES\n" + 
						"  ('"+Utils.generateKey()+"',\n" + 
						"   '"+dateFmt+"',\n" + 
						"   '"+methodName+"',\n" + 
						"   '"+reqXml+"',\n"+ 
						"   '"+resposeXml+"')";
		jdbcTemplate.execute(sql);
	}
}