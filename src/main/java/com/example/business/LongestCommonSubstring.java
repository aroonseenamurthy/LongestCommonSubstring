package com.example.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class LongestCommonSubstring {


	public List<String> process(List <String>strList){
		List <String> commonStrList = new ArrayList<String>();
		String commonStr = "";
		String largestStr = "";        
		String tempCommon = "";

		largestStr = getLargestString(strList, largestStr);

		char [] largeStringChars=largestStr.toCharArray();               
		for (char character: largeStringChars){
			tempCommon+= character;

			for (String s :strList){
				if(!s.contains(tempCommon)){
					tempCommon=Character.toString(character);
					for (String b :strList){
						if(!b.contains(tempCommon)){
							tempCommon="";
							break;
						}
					}
					break;
				}               
			}

			if(tempCommon!="" && tempCommon.length()>=commonStr.length()){
				commonStr=tempCommon;  
				commonStrList.add(commonStr);
			}                       
		}   
		commonStrList = convertListToATreeSet(commonStrList);
		return commonStrList;
	}

	private List<String> convertListToATreeSet(List<String> commonStrList) {
		Set<String> stringSet = new TreeSet<String>(commonStrList); //removes duplicates
		Map<String,Integer> treeMapOfString = convertSetToTreeMapBasedOnLenghtStringPair(stringSet);
		int longestStringLength = getLongestStringLength(stringSet);
		commonStrList.clear();
		for (Map.Entry<String, Integer> entry : treeMapOfString.entrySet())
		{
			if ( entry.getValue() == longestStringLength){
				commonStrList.add(entry.getKey());
			}
		}
		return commonStrList;
	}

	private Map<String, Integer> convertSetToTreeMapBasedOnLenghtStringPair(
			Set<String> stringSet) {
		Map<String,Integer> treeMapOfString = new TreeMap<String,Integer>();
		for ( String str : stringSet ) { //looping over uniqs
			treeMapOfString.put(str,str.length());
		}
		return treeMapOfString;
	}

	public static int getLongestStringLength(Set <String> stringSet) {
		int maxLength = 0;
		for (String s : stringSet) {
			if (s.length() > maxLength) {
				maxLength = s.length();
			}
		}
		return maxLength;
	}

	private String getLargestString(List<String> strList, String largestStr) {
		for (String str :strList) {
			if(largestStr.length() < str.length()){
				largestStr=str;
			}
		}
		return largestStr;
	}

}
