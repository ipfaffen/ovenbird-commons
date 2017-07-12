package com.ipfaffen.ovenbird.commons;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Isaias Pfaffenseller
 */
public class RandomUtil {
	
	public static final SecureRandom RANDOM = new SecureRandom();

	/**
	 * @param length
	 * @return
	 */
	public static String generateNumber(int length) {
		StringBuilder randomInt = new StringBuilder();
		for(int i = 0; i < length; i++) {
			randomInt.append(i == 0 ? (RANDOM.nextInt(9) + 1) : RANDOM.nextInt(10));
		}
		return randomInt.toString();
	}

	/**
	 * @return
	 */
	public static String generateRgb() {
		StringBuilder sb = new StringBuilder();
		sb.append("rgb(");
		sb.append(RANDOM.nextInt(255));
		sb.append(",");
		sb.append(RANDOM.nextInt(255));
		sb.append(",");
		sb.append(RANDOM.nextInt(255));
		sb.append(")");
		return sb.toString();
	}

	/**
	 * @return
	 */
	public static String generateUUID64() {
		StringBuilder sb = new StringBuilder();
		sb.append(UUID.randomUUID().toString());
		sb.append(UUID.randomUUID().toString());
		return sb.toString().replaceAll("-", "");
	}
	
	/**
	 * @return
	 */
	public static String generateUUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
     * @return
     */
    public static String generateUUIDShort() {
  	  UUID uuid = UUID.randomUUID();
  	  long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
  	  return Long.toString(l, Character.MAX_RADIX);
  	}

	/**
	 * @return
	 */
	public static String generateCode64() {
		StringBuilder sb = new StringBuilder();
		sb.append(MD5Encryptor.encrypt(System.nanoTime() + RandomUtil.generateNumber(10)));
		sb.append(MD5Encryptor.encrypt(RandomUtil.generateNumber(20)));
		return sb.toString();
	}
	
	/**
	 * @return
	 */
	public static String generateCode32() {
		return MD5Encryptor.encrypt(System.nanoTime() + RandomUtil.generateNumber(10));
	}
	
	/**
	 * Generate random sub list which the values can not be repeated.
	 * 
	 * @param list
	 * @param subListSize
	 * @return
	 */
	public static <T> List<T> generateSubList(List<T> list, int subListSize) {
		return generateSubList(list, subListSize, false);
	}
	
	/**
 	 * Generate random sub list.
 	 * 
	 * @param list
	 * @param subListSize
	 * @param repeatable - if the same list index can or cannot be repeated in sub list.
	 * @return
	 */
	public static <T> List<T> generateSubList(List<T> list, int subListSize, boolean repeatable) {
		List<T> subList = new ArrayList<T>(subListSize);
		List<Integer> indexes = new ArrayList<Integer>();
		while(subList.size() < subListSize) {
			int randomIndex = RANDOM.nextInt(list.size() - 1);
			if(!repeatable) {
				while(indexes.contains(randomIndex)) {
					randomIndex = (randomIndex > list.size()) ? 0 : (randomIndex + 1);
				}
			}
			indexes.add(randomIndex);
			subList.add(list.get(randomIndex));
		}
		return subList;
	}
	
	/**
	 * Generate random sub set which the values can not be repeated.
	 * 
	 * @param list
	 * @param subListSize
	 * @return
	 */
	public static <T> Set<T> generateSubSet(List<T> list, int subSetSize) {
		Set<T> subSet = new LinkedHashSet<T>(subSetSize);
		List<Integer> indexes = new ArrayList<Integer>();
		while(subSet.size() < subSetSize) {
			int randomIndex = RANDOM.nextInt(list.size() - 1);
			while(indexes.contains(randomIndex)) {
				randomIndex = (randomIndex > list.size()) ? 0 : (randomIndex + 1);
			}
			indexes.add(randomIndex);
			subSet.add(list.get(randomIndex));
		}
		return subSet;
	}
}