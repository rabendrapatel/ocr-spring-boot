package com.source.utill;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmailsData {
	private EmailsData(){
	}

	public static EmailsData getInstance(){
        return new EmailsData();
	}

	private String emailSubject;
	private String emailBody;
	private List<String> sentToEmail = new ArrayList<>();
	private List<String> ccToEmail = new ArrayList<>();
	private List<String> attachedment = new ArrayList<>();
}
