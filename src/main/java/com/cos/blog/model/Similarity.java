package com.cos.blog.model;

import java.util.HashSet;
import java.util.Set;

public class Similarity {
	public static double CalculateSimilarity(String str1, String str2) {
		// 문자열을 문자(또는 음절)의 집합으로 변환
		Set<Character> set1 = new HashSet<>();
		Set<Character> set2 = new HashSet<>();

		for (char c : str1.toCharArray()) {
			set1.add(c);
		}

		for (char c : str2.toCharArray()) {
			set2.add(c);
		}

		// 교차 집합 크기 계산
		int intersectionSize = 0;
		for (char c : set1) {
			if (set2.contains(c)) {
				intersectionSize++;
			}
		}

		// 합집합 크기 계산
		int unionSize = set1.size() + set2.size() - intersectionSize;

		// 자카드 유사도 계산
		if (unionSize == 0) {
			return 1.0; // 두 집합이 모두 비어 있을 경우
		} 
		else {
			return (double) intersectionSize / unionSize;
		}
	}
}
