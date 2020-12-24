// generator.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//

#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main()
{
    fstream newfile;
    newfile.open("texture_hd5.txt", ios::in); //open a file to perform read operation using file object
    if (newfile.is_open()) {   //checking whether the file is open
        int i = 4;
        int sizes[5] = { 259, 224, 217, 294, 231 };
        int n = sizes[i] / 7;
        string s;
        std::string fileName = "texture_hd" + std::to_string(i) + "converted.txt";
        std::ofstream outfile(fileName);

        for (int i = 0; i < n; i++) {
            // Read name
            getline(newfile, s); 
            std::string name = s;

            // Skip rotate line
            getline(newfile, s); 

            // Read xy
            getline(newfile, s); 
            int len = s.find(",") - 6;
            int x = stoi(s.substr(6, len));
            int y = std::stoi(s.substr(s.find(",") + 2));

            // Read size
            getline(newfile, s); 
            len = s.find(",") - 8;
            int w = stoi(s.substr(8, len));
            int h = stoi(s.substr(s.find(",") + 2));

            // Write everything in order
            outfile << "      name: " << name << std::endl;
            outfile << "      rect:" << std::endl;
            outfile << "        serializedVersion: 2" << std::endl;
            outfile << "        x: " << x << std::endl;
            outfile << "        y: " << 2048 - h - y << std::endl;
            outfile << "        width: " << w << std::endl;
            outfile << "        height: " << h << std::endl;


            // Skip the rest
            getline(newfile, s);
            getline(newfile, s);
            getline(newfile, s);

            outfile << "        ====================" << std::endl;
        }

        outfile.close();
        newfile.close(); //close the file object.
    }
}
