package com.hcl.SimpliLearn;

import java.io.IOException;

import filehandling.FileManager;

public class App 
{
    public static void main( String[] args ) throws IOException{
    	
    	FileManager filemanager = new FileManager();
    	
        // Display all poems in directory
    	filemanager.start();
    	filemanager.menu();
    }
}
