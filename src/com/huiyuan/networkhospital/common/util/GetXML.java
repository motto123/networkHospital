//package com.huiyuan.networkhospital.common.util;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.dom4j.Attribute;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.Node;
//import org.dom4j.io.SAXReader;
//
//import com.baidu.platform.comapi.map.t;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.util.Log;
//
//public class GetXML {
//	private String city_id[][] = null;
//	private String region_id[][][] = null;
//	private String[][] cityname = null;
//	private String[][][] region_name = null;
//
//	public GetXML(Context context) {
//
//		readcityXml(context);
//		// readregionXml(context);
//
//	}
//
//	public void readcityXml(Context context) {
//		int j1 = 0;
//		String a1[] = null;
//		String a2[] = null;
//		String a3[] = null;
//		try {
//			InputStream is = context.getAssets().open("city.xml");
//			Document doc = new SAXReader().read(is);
//			Element root = doc.getRootElement(); // 获取根节点
//			Element rootElt = doc.getRootElement(); // 获取根节点
//			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
//
//			a1 = new String[(rootElt.nodeCount() - 1) / 3];
//			a2 = new String[(rootElt.nodeCount() - 1) / 3];
//			a3 = new String[(rootElt.nodeCount() - 1) / 3];
//			for (int i = 0; i < rootElt.nodeCount(); i++) {
//				Node node = rootElt.node(i);
//				if (node instanceof Element) {
//					Element elementTemp = (Element) node;// 拿到第一个二级节点的名称do
//					// 取得二级节点do的type和path属性的值
//					for (Iterator iter = elementTemp.attributeIterator(); iter
//							.hasNext();) {
//						Attribute item = (Attribute) iter.next(); // 拿到二级节点的path和type
//					}
//					// 获取二级节点的下面的子节点forward（三级节点）
//					for (Iterator iterroot2 = elementTemp.elementIterator(); iterroot2
//							.hasNext();) {
//						Element root22 = (Element) iterroot2.next(); // 得到一个二级节点
//						try {
//							if (root22.getName().equals("city_id")) {
//								// Log.e("11111111111", "" + root22.getText());
//								a1[j1] = root22.getText();
//							}
//							if (root22.getName().equals("city_name")) {
//								a2[j1] = root22.getText();
//							}
//							if (root22.getName().equals("province_id")) {
//								a3[j1] = root22.getText();
//								j1++;
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//			int k = 0;
//			cityname = new String[1][a3.length];
//			city_id = new String[1][a3.length];
//			for (int i = 0; i < a3.length; i++) {
//				if (a3[i].equals("510000")) {
//					cityname[0][k] = a2[i];
//					city_id[0][k] = a1[i];
//					k++;
//				}
//			}
//			//
//			// for (int i = 0; i < a3.length; i++) {
//			// System.out.println(city_id[0][i]);
//			// }
//			setCity_id(city_id);
//			setCityname(cityname);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			Log.e("1", "1");
//			e.printStackTrace();
//			Log.e("22", "22");
//		}
//	}
//
//	public void readregionXml(Context context) {
//		int all = 0;
//		int[] classs = new int[21];// 四川有21个市，第一个市的区为classs[0]
//		int classkey = 0;
//		boolean ckey = false;// 因为只有四川的，所以找到四川第一个市之后开始录入数据
//		try {
//			InputStream is = context.getAssets().open("region.xml");
//			Document doc = new SAXReader().read(is);
//			Element root = doc.getRootElement(); // 获取根节点
//			Element rootElt = doc.getRootElement(); // 获取根节点
//			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
//			for (int i = 0; i < rootElt.nodeCount(); i++) {
//				Node node = rootElt.node(i);
//				if (node instanceof Element) {
//					Element elementTemp = (Element) node;// 拿到第一个二级节点的名称do
//					// 取得二级节点do的type和path属性的值
//					for (Iterator iter = elementTemp.attributeIterator(); iter
//							.hasNext();) {
//						Attribute item = (Attribute) iter.next(); // 拿到二级节点的path和type
//					}
//					// 获取二级节点的下面的子节点forward（三级节点）
//					for (Iterator iterroot2 = elementTemp.elementIterator(); iterroot2
//							.hasNext();) {
//						Element root22 = (Element) iterroot2.next(); // 得到一个二级节点
//						if (root22.getName().equals("region_id")) {
//							all++;
//						}
//						if (root22.getName().equals(cityname[0][0])) {
//							ckey = true;
//						}
//						if (ckey) {
//							if (classkey < 21) {
//								if (root22.getName().equals(
//										cityname[0][classkey])) {
//									classs[classkey]++;
//								} else {
//									classkey++;
//									classs[classkey]++;
//								}
//							} else {
//								ckey = false;
//							}
//
//						}
//					}
//				}
//			}
//			String[] a1 = new String[all];
//			String[] a2 = new String[all];
//			String[] a3 = new String[all];
//			for (int i = 0; i < rootElt.nodeCount(); i++) {
//				int key = 0;
//				Node node = rootElt.node(i);
//				if (node instanceof Element) {
//					Element elementTemp = (Element) node;// 拿到第一个二级节点的名称do
//					// 取得二级节点do的type和path属性的值
//					for (Iterator iter = elementTemp.attributeIterator(); iter
//							.hasNext();) {
//						Attribute item = (Attribute) iter.next(); // 拿到二级节点的path和type
//					}
//					// 获取二级节点的下面的子节点forward（三级节点）
//					for (Iterator iterroot2 = elementTemp.elementIterator(); iterroot2
//							.hasNext();) {
//						Element root22 = (Element) iterroot2.next(); // 得到一个二级节点
//						if (root22.getName().equals("region_id")) {
//							a1[key] = root22.getText();
//						}
//						if (root22.getName().equals("region_name")) {
//							a2[key] = root22.getText();
//						}
//						if (root22.getName().equals("city_id")) {
//							a3[key] = root22.getText();
//						}
//					}
//				}
//			}
//			
//			
//			
//			
//			region_name = new String[1][cityname.length][all];
//			region_id = new String[1][cityname.length][all];
//			// int k = 0;
//			// int jk = 0;
//			// region_name = new String[1][cityname.length][all];
//			// region_id = new String[1][cityname.length][all];
//			// try {
//			// int[] b = new int[cityname.length];
//			// for (int i = 0; i < city_id[0].length; i++) {
//			// for (int j = 0; j < a3.length; j++) {
//			// if (city_id[0][jk].equals(a3[j])) {
//			// b[jk]++;
//			// }
//			// }
//			// jk++;
//			// }
//			// jk = 0;
//			// for (int i = 0; i < b.length; i++) {
//			// Log.e("b[jk]b[jk]b[jk]b[jk]b[jk]b[jk]b[jk]b[jk]b[jk]b[jk]",
//			// "" + b[jk]);
//			// }
//			// for (int i = 0; i < a3.length; i++) {
//			// if (a3[i].equals(city_id[0][jk].toString())) {
//			// for (int j = 0; j < b[jk]
//			// /* 这里是每个区有多少个，填个数 */
//			// ; j++) {
//			// region_name[0][jk][j] = a2[i];
//			// region_id[0][jk][j] = a1[i];
//			// i++;
//			// }
//			// jk++;
//			// }
//			// }
//			// } catch (Exception e) {
//			// // TODO: handle exception
//			// }
//			setRegion_name(region_name);
//			setRegion_id(region_id);
//
//		} catch (DocumentException e) {
//			e.printStackTrace();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public String[][] getCity_id() {
//		return city_id;
//	}
//
//	public String[][][] getRegion_id() {
//		return region_id;
//	}
//
//	public String[][] getCityname() {
//		return cityname;
//	}
//
//	public String[][][] getRegion_name() {
//		return region_name;
//	}
//
//	public void setCity_id(String[][] city_id) {
//		this.city_id = city_id;
//	}
//
//	public void setRegion_id(String[][][] region_id) {
//		this.region_id = region_id;
//	}
//
//	public void setCityname(String[][] cityname) {
//		this.cityname = cityname;
//	}
//
//	public void setRegion_name(String[][][] region_name) {
//		this.region_name = region_name;
//	}
//
//}
