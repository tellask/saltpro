package marst.component;

import java.util.Comparator;

import com.subject.pojo.SystemMenus;
/**
 * 系统菜单类的排序工具类
 * @author taddy
 *
 */
public class SystemMenusComparator implements Comparator<SystemMenus> {

	@Override
	public int compare(SystemMenus arg1, SystemMenus arg2) {
		//根据MenusCode进行排序
		int menusCode1=Integer.parseInt(arg1.getMenusCode());
		int menusCode2=Integer.parseInt(arg2.getMenusCode());
		if(menusCode1>menusCode2){
			return 1;	//由低到高排序
		}else if (menusCode1<menusCode2) {
			return -1;	//由高到低排序
		}else{
			return 0;
		}
	}
}
