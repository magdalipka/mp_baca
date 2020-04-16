package ZeznaniaPodatkowe;

// Magdalena Lipka gr. 1//
import java.util.Scanner;
class Source {
	public static Scanner in=new Scanner(System.in);
	public static void main( String [] args ) {
	int Sets=in.nextInt();
	for(int i=0; i<Sets; i++) {
		int Quan=in.nextInt();
		int [] Data=new int[Quan];
		int States=1;
		Data[0]=in.nextInt();
		for(int j=1; j<Quan; j++) {
		Data[j]=in.nextInt();
		if(Data[j]!=Data[j-1]) States++;}
		int Questions=in.nextInt();
		for(int j=0; j<Questions; j++) {
		int left=in.nextInt();
		int right=in.nextInt();
		int l=0;
		int r=Quan-1;
		int m;
		while (l<r) {
			m=(l+r)/2;
			if(Data[m]<left) l=m+1;
			else r=m;}
		int l_in=l;
		if(l_in-1>=0 && Data[l_in-1]>=left) l_in++;
		l=0;
		r=Quan-1;
		while (l<r) {
			m=(l+r)/2;
			if(Data[m]>right) r=m;
			else l=m+1;}
		int r_in=r-1;
		if(r_in+1<Quan && Data[r_in+1]<=right) r_in++;
		int sum;
        if((r_in==l_in && (Data[r_in]<left || Data[r_in]>right)) || r_in<l_in) sum=0;
		else sum=r_in-l_in+1;
		System.out.println(sum);}
	System.out.println(States);}}}