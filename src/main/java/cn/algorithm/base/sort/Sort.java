package cn.algorithm.base.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author whz 
 * @version on:2019-3-2 下午02:30:46 
 */
public class Sort implements ISort{
//	@Test
	public void bubbleSort(){
		int[] data={3,6,9,8,5,2,1,4,7};
//      基础版
		//经过length-1轮比较
		for(int i=0;i<data.length;i++){
			//经过length-1-i轮比较
			for(int j=i+1;j<data.length;j++){
				//前面的数大就交换位置，让小数“上冒”，大数“沉底”
				if(data[i]>data[j]){
					int temp=data[i];
					data[i]=data[j];
					data[j]=temp;
				}
			}
		}

//      设置标志变量优化版
		/*for(int i=1;i<data.length;i++){
			//设设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。可以减少比较时间
			boolean flag=true;
			for(int j=0;j<data.length-i;j++){
				if(data[j]>data[j+1]){
					int temp=data[j+1];
					data[j+1]=data[j];
					data[j]=temp;
					flag=false;
				}
			}
			if(flag){
				break;
			}
		}*/
		System.out.println(Arrays.toString(data));
	}
//	@Test
	@Override
	public void selectionSort() {
		int[] data={3,6,9,8,5,2,1,4,7};
		// 总共要经过 N-1 轮比较
		 for (int i = 0; i < data.length - 1; i++) {
			 int min = i;
			 // 每轮需要比较的次数 N-i
			 for (int j = i + 1; j < data.length; j++) {
				 if (data[j] < data[min]) {
					 // 记录目前能找到的最小值元素的下标
					 min = j;
				 }
			 }
			// 将找到的最小值和i位置所在的值进行交换
			if (i != min) {
				int tmp = data[i];
				data[i] = data[min];
				data[min] = tmp;
			}
		}
		 System.out.println(Arrays.toString(data));
	}


	@Override
	@Test
	public void insertSort() {
		int[] data={3,6,9,8,5,2,1,4,7};
		
		/*//i相当于移动时的指针，指向那个下标就表示移动到哪儿了
		for(int i=1; i<data.length; i++){
			//从前往后移动，当前比前面的小就向前移动，移动时做比较移动到合适的位置
			for(int j=0; j<i; j++){
				if(j==0){
					if(data[i] < data[j]){
						int tmp = data[i];
						data[i] = data[j];
						data[j] = tmp;
					}
				}else if(data[i] > data[j-1] && data[i] < data[j]){
					int tmp = data[i];
					data[i] = data[j];
					data[j] = tmp;
				}
			}
		}*/

		for(int i = 1; i <data.length; i++){
			int temp = data[i];
			int j = i;
			if(data[j-1] > temp){
				while (j >= 1 && data[j-1] >temp){
					data[j] = data[j-1];
					j--;
				}
				data[j] = temp;
			}
		}
		
		//从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
		/*for(int i=1; i<data.length; i++){
			int temp=data[i];//记录需要插入的数
			// 从已经排序的序列最右边的开始比较，找到比其小的数
			int j=i;
			while(j>0 && temp<data[j-1]){
				data[j] = data[j-1];
				j--;
			}
			// 存在比其小的数，插入
			if(i != j){
				data[j] = temp;
			}
		}*/
		System.out.println(Arrays.toString(data));
	}

	
	@Override
	public void shellSort() {
		int[] data={3,6,9,8,5,2,1,4,7};
		
		int gap = 1;
		while (gap < data.length) {
			gap = gap * 3 + 1;
		}
		while (gap > 0) {
			for (int i = gap; i < data.length; i++) {
					int tmp = data[i];
					int j = i - gap;
					while (j >= 0 && data[j] > tmp) {
						data[j + gap] = data[j];
						j -= gap;
					}
					data[j + gap] = tmp;
			}
			gap = (int) Math.floor(gap / 3);
		}
		System.out.println(Arrays.toString(data));
	}

//	@Test
	@Override
	public void mergeSort() {
		/*int[] arr={3,6,9,8,5,2,1,4,7};
		if (arr.length < 2) {
			 return arr;
			}
			 int middle = (int) Math.floor(arr.length / 2);
			int[] left = Arrays.copyOfRange(arr, 0, middle);
			int[] right = Arrays.copyOfRange(arr, middle, arr.length);
			return merge(sort(left), sort(right));
			}
			protected int[] merge(int[] left, int[] right) {
			int[] result = new int[left.length + right.length];
			int i = 0;
			while (left.length > 0 && right.length > 0) {
				if (left[0] <= right[0]) {
					result[i++] = left[0];
					left = Arrays.copyOfRange(left, 1, left.length);
				} else {
					result[i++] = right[0];
					right = Arrays.copyOfRange(right, 1, right.length);
				}
			}
			while (left.length > 0) {
				result[i++] = left[0];
				left = Arrays.copyOfRange(left, 1, left.length);
			}
			while (right.length > 0) {
				result[i++] = right[0];
				right = Arrays.copyOfRange(right, 1, right.length);
			}*/
	}
//	@Test
	@Override
	public void quickSort() {
		int[] arr={3,6,9,8,5,2,1,4,7};
		quickSort(arr, 0, arr.length-1);
		System.out.println(Arrays.toString(arr));
	}
	public static void quickSort(int[] arr,int start,int end) {
		if(start >= end) {
			return;
		}
		int i,j,index;
		i = start;
		j = end;
		index = arr[i];
		
		while(i < j) {
			while (i < j && arr[j] >= index) {
				j--;
			}
			while (i < j && arr[i] < index) {
				i++;
			}
			if(i < j) {
				int empt = arr[i];
				arr[i] = arr[j];
				arr[j] = empt;
			}
		}
		if(arr[i] < arr[start]) {
			int empt = arr[i];
			arr[i] = arr[start];
			arr[start] = empt;
		}
		quickSort(arr, start, i - 1);
		quickSort(arr, i + 1, end);
	}


	@Override
	public void heapSort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void countingSort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bucketSort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void radixSort() {
		// TODO Auto-generated method stub
		
	}
}
