package tmall.util;
 
import java.util.List;

import org.springframework.data.domain.Page;
 
public class Page4Navigator<T> {
	//jpa ���ݳ����ķ�ҳ���� Page4Navigator ����Ƕ������з�װ�Դﵽ��չ��Ч��
	Page<T> pageFromJPA;
	 
	//��ҳ��ʱ�� ,�����ҳ���Ƚ϶࣬��ô��ʾ�����ķ�ҳ����һ���м����� ���������ҳ�����ĳ����������ģ� [8,9,10,11,12], ��ô navigatePages ����5
	int navigatePages;
	  
	//��ҳ����
	int totalPages;
	 
	//�ڼ�ҳ����0��
	int number;
	  
	//�ܹ��ж���������
	long totalElements;
	  
	//һҳ����ж���������
	int size;
	 
	//��ǰҳ�ж��������� (�� size����ͬ���ǣ����һҳ���ܲ��� size ��)
	int numberOfElements;
	 
	//���ݼ���
	List<T> content;
	 
	//�Ƿ�������
	boolean isHasContent;
	 
	//�Ƿ�����ҳ
	boolean first;
	 
	//�Ƿ���ĩҳ
	boolean last;
	  
	//�Ƿ�����һҳ
	boolean isHasNext;
	 
	//�Ƿ�����һҳ
	boolean isHasPrevious;
	  
	//��ҳ��ʱ�� ,�����ҳ���Ƚ϶࣬��ô��ʾ�����ķ�ҳ����һ���м����� ���������ҳ�����ĳ����������ģ� [8,9,10,11,12]����ô navigatepageNums ����������飺[8,9,10,11,12]����������ǰ��չʾ
	int[] navigatepageNums;
     
    public Page4Navigator() {
        //����յķ�ҳ��Ϊ�� Redis �� json��ʽת��Ϊ Page4Navigator �����ר���ṩ��
    }
     
    public Page4Navigator(Page<T> pageFromJPA,int navigatePages) {
        this.pageFromJPA = pageFromJPA;
        this.navigatePages = navigatePages;
         
        totalPages = pageFromJPA.getTotalPages();
         
        number  = pageFromJPA.getNumber() ;
         
        totalElements = pageFromJPA.getTotalElements();
         
        size = pageFromJPA.getSize();
         
        numberOfElements = pageFromJPA.getNumberOfElements();
         
        content = pageFromJPA.getContent();
         
        isHasContent = pageFromJPA.hasContent();
                 
        first = pageFromJPA.isFirst();
         
        last = pageFromJPA.isLast();
         
        isHasNext = pageFromJPA.hasNext();
         
        isHasPrevious  = pageFromJPA.hasPrevious();       
         
        calcNavigatepageNums();
         
    }
 
    private void calcNavigatepageNums() {
        int navigatepageNums[];
        int totalPages = getTotalPages();
        int num = getNumber();
        //����ҳ��С�ڻ���ڵ���ҳ����ʱ
        if (totalPages <= navigatePages) {
            navigatepageNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //����ҳ�����ڵ���ҳ����ʱ
            navigatepageNums = new int[navigatePages];
            int startNum = num - navigatePages / 2;
            int endNum = num + navigatePages / 2;
 
            if (startNum < 1) {
                startNum = 1;
                //(��ǰnavigatePagesҳ
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > totalPages) {
                endNum = totalPages;
                //���navigatePagesҳ
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //�����м�ҳ
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }  
        this.navigatepageNums = navigatepageNums;
    }
 
    public int getNavigatePages() {
        return navigatePages;
    }
 
    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }
 
    public int getTotalPages() {
        return totalPages;
    }
 
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
 
    public int getNumber() {
        return number;
    }
 
    public void setNumber(int number) {
        this.number = number;
    }
 
    public long getTotalElements() {
        return totalElements;
    }
 
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
 
    public int getSize() {
        return size;
    }
 
    public void setSize(int size) {
        this.size = size;
    }
 
    public int getNumberOfElements() {
        return numberOfElements;
    }
 
    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
 
    public List<T> getContent() {
        return content;
    }
 
    public void setContent(List<T> content) {
        this.content = content;
    }
 
    public boolean isHasContent() {
        return isHasContent;
    }
 
    public void setHasContent(boolean isHasContent) {
        this.isHasContent = isHasContent;
    }
 
    public boolean isFirst() {
        return first;
    }
 
    public void setFirst(boolean first) {
        this.first = first;
    }
 
    public boolean isLast() {
        return last;
    }
 
    public void setLast(boolean last) {
        this.last = last;
    }
 
    public boolean isHasNext() {
        return isHasNext;
    }
 
    public void setHasNext(boolean isHasNext) {
        this.isHasNext = isHasNext;
    }
 
    public boolean isHasPrevious() {
        return isHasPrevious;
    }
 
    public void setHasPrevious(boolean isHasPrevious) {
        this.isHasPrevious = isHasPrevious;
    }
 
    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }
 
    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }
 
}
