package com.JFSCapstoneBackend.Service;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class Constants {

	public static final SimpleDateFormat dateFormat =
			new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");
	
	public static final String ACTIVE="Active";
	public static final String EXPIRED="Expired";
	
}
