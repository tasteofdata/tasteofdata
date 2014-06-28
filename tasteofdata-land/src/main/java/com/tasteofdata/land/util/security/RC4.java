package com.tasteofdata.land.util.security;


/**
 * RC4机密算法
 * @author wwj
 *
 */
public class RC4 {
	private static final int BOX_LEN = 256;		//状态数组长度
	
	private static void swap(Integer[] s, int i, int j) {
		int temp;
		temp = s[i];
		s[i] = s[j];
		s[j] = temp;
	}
	
	
	/**
	 * RC4加解密
	 * @param data
	 * @param secretKey 密钥
	 * @return
	 */
	public static byte[] encrypt(byte[] data,byte[] secretKey){
		int keyint[] = byteArray2Int(secretKey);	//密钥int数组
		
		Integer s_box[] = new Integer[BOX_LEN];
		Integer k_box[] = new Integer[BOX_LEN];
		
		int i,j,t;
		//The key-scheduling algorithm (KSA) 
		for(i=0;i<BOX_LEN;i++){
			s_box[i] = i;
			k_box[i] = keyint[i %  keyint.length];
		}
		j=0;
		for(i=0;i<BOX_LEN;i++){
			j = (j + s_box[i] + k_box[i]) % BOX_LEN;
			swap(s_box,i,j);
		}
		byte encrypt[] = new byte[data.length];
		i=0;
		j=0;
		t=0;
		for (int k = 0; k < data.length; k++) {
			i = (i + 1) % BOX_LEN;
			j = (j + s_box[i]) % BOX_LEN;
			swap(s_box, i, j);
			t = (s_box[i] + s_box[j]) % BOX_LEN;
			encrypt[k] =  (byte) (s_box[t] ^ data[k]);
		}
		return encrypt;
	}
	
	private static int[] byteArray2Int(byte[] byteArray){
		int[] intArray = new int[byteArray.length];
		for(int i=0;i<byteArray.length;i++){
			intArray[i] = byteArray[i] & 0xFF;
		}
		return intArray;
	}
	
	/**
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte str[] = "wangweijie".getBytes();
		byte key[] = "password".getBytes();
		byte b[] = RC4.encrypt(str, key);
		String s = new String( b,"utf-8");
		System.out.println(s);
		System.out.println(new String(RC4.encrypt(b, key),"utf-8"));
	}*/
	
}
