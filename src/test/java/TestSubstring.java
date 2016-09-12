import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TestSubstring {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ss = "pd_allfund_dd";
		String pre = ss.substring(0, ss.indexOf("_", 4));
		System.out.println("ss.indexOf:"+ss.indexOf("_",4));
		System.out.println("pre:"+pre);
		
		
		List<String> oldL = Lists.newArrayList("1","3","5","7");
		List<String> newL = Lists.newArrayList("1","2","4","7");
		for(String old: oldL){
			System.out.println("--1--:"+old);
		}
		oldL.addAll(newL);
		System.out.println("====================");
		for(String old: oldL){
			System.out.println("--2--:"+old);
		}
		System.out.println("====================");
		List<Object> nnList = oldL.stream().distinct().collect(Collectors.toList());
		
		for(Object old: nnList){
			System.out.println("--3--:"+old);
		}
//		oldL.stream().map(c->c.toLowerCase()).collect(Collectors.toList());
		
		Set<String> set = Sets.newHashSet();
		set.add("1");
		
		set.add("1");
		
 	}

}
