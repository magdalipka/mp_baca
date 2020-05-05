#include <fstream>
#include <ctime>
#include <cstdlib>
using namespace std;

string genS () {
	string s = "";
	int a = rand()%4;
	if (a == 1) s += "A";
	else if (a == 2) s += 'C';
	else if (a == 3) s += 'G';
	else s += 'T';
	a = rand()%4;
	if (a == 1) s += "A";
	else if (a == 2) s += 'C';
	else if (a == 3) s += 'G';
	else s += 'T';
	a = rand()%4;
	if (a == 1) s += "A";
	else if (a == 2) s += 'C';
	else if (a == 3) s += 'G';
	else s += 'T';
	return s; 
}

int main(int argc, char* argv[]) {
	srand(time(NULL));
	ofstream f;
	f.open(argv[2]);
	f << "1\n";
	f << "ATG";
	string tab[20];
	int ilosc = stoi(argv[1]);
	for ( int i = 0; i < ilosc; i++ ) {
		string pom = genS();
		if ( pom == "ATG" || pom == "TGA" || pom == "TAG" || pom == "TAA" ) {
			i--;
			continue;
		}
		tab[i] = pom;
		f << pom; 
	}
	f << "TAG";

	for ( int i = 0; i < ilosc; i++ ) {
		for ( int j = 0; j < ilosc-1; j++ ) {
			if ( tab[j] > tab[j+1] ) {
				string pom = tab[j];
				tab[j] = tab[j+1];
				tab[j+1] = pom;
			}
		}
	}
	ofstream ff;
	ff.open(argv[3]);
	for ( int i = 0; i < ilosc; i++ ) {
		ff << tab[i];
	}
}