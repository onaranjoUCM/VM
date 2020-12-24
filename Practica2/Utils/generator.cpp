// generator.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//

#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main()
{
    fstream newfile;
    newfile.open("levels.txt", ios::in); //open a file to perform read operation using file object
    if (newfile.is_open()) {   //checking whether the file is open
        string tp;
        int i = 0;
        while (getline(newfile, tp)) { //read data from file object and put it into string.
            i++;
            std::string fileName = std::to_string(i) + ".txt";
            std::ofstream outfile(fileName);
            outfile << tp << std::endl;
            outfile.close();
        }
        newfile.close(); //close the file object.
    }
}

// Ejecutar programa: Ctrl + F5 o menú Depurar > Iniciar sin depurar
// Depurar programa: F5 o menú Depurar > Iniciar depuración

// Sugerencias para primeros pasos: 1. Use la ventana del Explorador de soluciones para agregar y administrar archivos
//   2. Use la ventana de Team Explorer para conectar con el control de código fuente
//   3. Use la ventana de salida para ver la salida de compilación y otros mensajes
//   4. Use la ventana Lista de errores para ver los errores
//   5. Vaya a Proyecto > Agregar nuevo elemento para crear nuevos archivos de código, o a Proyecto > Agregar elemento existente para agregar archivos de código existentes al proyecto
//   6. En el futuro, para volver a abrir este proyecto, vaya a Archivo > Abrir > Proyecto y seleccione el archivo .sln
