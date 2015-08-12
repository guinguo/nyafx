package com.github.cuter44.nyafx.servlet;

import static com.github.cuter44.nyafx.servlet.Params.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class ComSearch {
	public static CharSequence cs = ",";
	
	public static DetachedCriteria CSearch(Object obj,HttpServletRequest req){
		Class<?> clazz = obj.getClass();
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		Field[] field = clazz.getDeclaredFields();
		
		//测试反射
		/*for(Field f :field){
			System.out.println(f);
			String type = f.getType().toString();
			System.out.println("类型"+type.substring(type.lastIndexOf(".")+1)+"属性名"+f.getName());
		}*/
		
		
		Enumeration<String> v = (Enumeration<String>)req
				.getParameterNames();//获取请求的数据
		
		while(v.hasMoreElements()){
			
			String name = (String) v.nextElement();//<input>或者post里面的name
			String value = req.getParameter(name);//<input>或者post里面的name的值
			
			for(Field f :field){//遍历类里面的所有属性域
				
				String type = f.getType().toString();//获得搜索类中数据类型
				if(type.contains(".")){
					type = type.substring(type.lastIndexOf(".")+1);
				}
				String propertyName = f.getName();//获得类中属性的名称
				
				if(name.equals(propertyName.trim())){
					if(type.equals("int")||type=="int"){
							if(value.contains(cs)){
								Integer[] INTS = getIntArray(req,name);
								if(INTS!=null){
									dc.add(Restrictions.in(propertyName, INTS));
								}
							}
							else{
								Integer   INT = getInt(req,name);
								if(INT!=null){
									dc.add(Restrictions.eq(propertyName, INT));
								}
							}
					}
					
					else if(type.equals("Boolean")||type=="Boolean"){
							Boolean BOOLEAN = getBoolean(req, name);
							if(BOOLEAN!=null){
								dc.add(Restrictions.eq(propertyName, BOOLEAN));
							}
					}
					
					else if(type.equals("Long")||type=="Long"){
							if(value.contains(cs)){
								List<Long> LONGS = getLongList(req,name);
								if(LONGS!=null){
									dc.add(Restrictions.in(propertyName, LONGS));
								}
							}
							else{
								List<Long>   LONG = getLongList(req,name);
								if(LONG!=null){
									dc.add(Restrictions.eq(propertyName, LONG));
								}
							}
					}
					
					else if(type.equals("Byte")||type=="Byte"){
							if(value.contains(cs)){
								byte[] BYTES = getByteArray(req,name);
								if(BYTES!=null){
									dc.add(Restrictions.eq(propertyName, BYTES));
								}
							}
							else{
								Byte BYTE = getByte(req,name);
								if(BYTE!=null){
									dc.add(Restrictions.eq(propertyName, BYTE));
								}
							}
					}
					
					else if(type.equals("Float")||type=="Float"){
							Float   FLOAT = getFloat(req,name);
							if(FLOAT!=null){
								dc.add(Restrictions.eq(propertyName, FLOAT));
							}
					}
					
					else if(type.equals("Double")||type=="Double"){
							if(value.contains(cs)){
								Double[] DOUBLES = getDoubleArray(req,name);
								if(DOUBLES!=null){
									dc.add(Restrictions.in(propertyName, DOUBLES));
								}
							}
							else{
								Double   DOUBLE = getDouble(req,name);
								if(DOUBLE!=null){
									dc.add(Restrictions.eq(propertyName, DOUBLE));
								}
							}
					}
					
					else if(type.equals("String")||type=="String"){
							if(value.contains(cs)){
								String[] STRINGS = getStringArray(req,name);
								if(STRINGS!=null){
									dc.add(Restrictions.in(propertyName, STRINGS));
								}
							}
							else{
								String   STRING = getString(req,name);
								if(STRING!=null){
									dc.add(Restrictions.like(propertyName, STRING));	
								}
							}
					}
					
					else if(type.equals("Date")||type=="Date"){
							if(value.contains(cs)){
								Date[]   DATES = getDateArray(req,name);
								if (DATES[0] != null)
				                    dc.add(Restrictions.gt("tmReserve", DATES[0]));
				                if (DATES[1] != null)
				                    dc.add(Restrictions.le("tmReserve", DATES[1]));
							}
							else{
								Date   DATE = getDate(req,name);
								if(DATE!=null){
									dc.add(Restrictions.eq(propertyName, DATE));	
								}
							}
					}
					
					else if(getLongList(req,name)!=null){
						List<Long> l =  getLongList(req,name);
						if(l!=null){
							System.out.println(l+"======");
							dc.createAlias(propertyName, propertyName)
							.add(Restrictions.in(propertyName+".id", l));	
						}
					}
				}
			 }
		}
	return dc;
}
}
