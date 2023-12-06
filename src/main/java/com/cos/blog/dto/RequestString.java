package com.cos.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestString {
	public String placeId;
	public String delete_location;
	public String bell;
	public String diaper_change;
	public String disabled;
	public String report_location_name;
	public String report_location_point;
	public String user_opinion;
}
