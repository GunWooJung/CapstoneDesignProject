package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Opinion;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;
import com.cos.blog.repository.OpinionRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.PlaceTrashRepository;
import com.cos.blog.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private PlaceTrashRepository placetrashRepository;

	@Autowired
	private OpinionRepository opinionRepository;

	@Transactional
	public List<Report> reportShow(int placeId) {
		Optional<Place> place = placeRepository.findById(placeId);
		if (place.isPresent()) {
			List<Report> reports = reportRepository.findByPlace(place.get());
			return reports;
		} else {
			return null;
		}
	}

	@Transactional
	public ResponseEntity<String> reportEnroll(String placeId, String type, String content, String ip) {
		Optional<Place> place = placeRepository.findById(Integer.parseInt(placeId));
		if (place.isPresent()) {
			String contentKor = content;
			if (contentKor.equals("man_bell_yes"))
				contentKor = "남자 화장실 비상벨이 있음";
			else if (contentKor.equals("man_bell_no"))
				contentKor = "남자 화장실 비상벨이 없거나 파손됨";
			else if (contentKor.equals("woman_bell_yes"))
				contentKor = "여자 화장실 비상벨이 있음";
			else if (contentKor.equals("woman_bell_no"))
				contentKor = "여자 화장실 비상벨이 없거나 파손됨";
			else if (contentKor.equals("disabled_bell_no"))
				contentKor = "장애인 화장실 비상벨이 없거나 파손됨";
			else if (contentKor.equals("disabled_bell_yes"))
				contentKor = "장애인 화장실 비상벨이 있음";
			else if (contentKor.equals("delete_closed"))
				contentKor = "폐업";
			else if (contentKor.equals("delete_duplicated"))
				contentKor = "중복된 장소";
			else if (contentKor.equals("delete_nowhere"))
				contentKor = "없는 장소";
			else if (contentKor.equals("delete_relocation"))
				contentKor = "다른 곳으로 이전";
			else if (contentKor.equals("man_diaper_yes"))
				contentKor = "남자 화장실 기저귀 교환대가 있음";
			else if (contentKor.equals("man_diaper_no"))
				contentKor = "남자 화장실 기저귀 교환대가 없거나 파손됨";
			else if (contentKor.equals("woman_diaper_yes"))
				contentKor = "여자 화장실 기저귀 교환대가 있음";
			else if (contentKor.equals("woman_diaper_no"))
				contentKor = "여자 화장실 기저귀 교환대가 없거나 파손됨";
			else if (contentKor.equals("man_disabled_yes"))
				contentKor = "남자 장애인 화장실이 있음";
			else if (contentKor.equals("man_disabled_no"))
				contentKor = "남자 장애인 화장실이 없음";
			else if (contentKor.equals("woman_disabled_yes"))
				contentKor = "여자 장애인 화장실이 있음";
			else if (contentKor.equals("woman_disabled_no"))
				contentKor = "여자 장애인 화장실이 없음";
			Report report = new Report();
			report.setType(type);
			report.setContent(contentKor);
			report.setPlace(place.get());
			report.setCount(1);
			report.setIp(ip);
			Boolean isSame = false;
			List<Report> calsame = reportRepository.findByPlace(place.get());
			for (Report r : calsame) {
				if (r.getType().equals(type) && r.getContent().equals(contentKor)) {
				
					if (r.getIp().equals(ip)) {
						isSame = true;
						return ResponseEntity.status(400).body("ip");
					} else if (r.getIp2().equals(ip)) {
						isSame = true;
						return ResponseEntity.status(400).body("ip");
					} else if (r.getIp3().equals(ip)) {
						isSame = true;
						return ResponseEntity.status(400).body("ip");
					} 
					else {
						isSame = true;
						r.setCount(r.getCount() + 1);
						if (r.getIp2() == null)
							r.setIp2(ip);
						else if (r.getIp3() == null)
							r.setIp3(ip);
						System.out.println(r.getCount());
						if (r.getCount() >= 3) {
							if (r.getType().equals("장소 삭제")) {
								placeRepository.deleteById(place.get().getId());
							}
							else if (r.getType().equals("비상벨 정보 수정")) {
								if (r.getContent().equals("남자 화장실 비상벨이 있음")) {
									String s = place.get().getEmergency_bell() + "남자";
									place.get().setEmergency_bell(s);
									placeRepository.save(place.get());
								} else if (r.getContent().equals("남자 화장실 비상벨이 없거나 파손됨")) {
									String inputString = place.get().getEmergency_bell();
									String s = inputString.replaceAll("(?i)" + "남자", "");
									place.get().setEmergency_bell(s);
									placeRepository.save(place.get());
								} else if (r.getContent().equals("여자 화장실 비상벨이 있음")) {
									String s = place.get().getEmergency_bell() + "여자";
									place.get().setEmergency_bell(s);
									placeRepository.save(place.get());
								} else if (r.getContent().equals("여자 화장실 비상벨이 없거나 파손됨")) {
									String inputString = place.get().getEmergency_bell();
									String s = inputString.replaceAll("(?i)" + "여자", "");
									place.get().setEmergency_bell(s);
									placeRepository.save(place.get());
								} else if (r.getContent().equals("장애인 화장실 비상벨이 있음")) {
									String s = place.get().getEmergency_bell() + "장애";
									place.get().setEmergency_bell(s);
									placeRepository.save(place.get());
								} else if (r.getContent().equals("장애인 화장실 비상벨이 없거나 파손됨")) {
									String inputString = place.get().getEmergency_bell();
									String s = inputString.replaceAll("(?i)" + "장애", "");
									place.get().setEmergency_bell(s);
									placeRepository.save(place.get());
								}
							} 
							else if (r.getType().equals("기저귀 교환대 정보 수정")) {
								if (r.getContent().equals("남자 화장실 기저귀 교환대가 있음")) {
									if (place.get().getDiaper().equals("없음")) {
										place.get().setDiaper("남자");
									} else if (place.get().getDiaper().equals("여자")) {
										place.get().setDiaper("남여");
									}
									placeRepository.save(place.get());
								} 
								else if (r.getContent().equals("남자 화장실 기저귀 교환대가 없거나 파손됨")) {
									if (place.get().getDiaper().equals("남자")) {
										place.get().setDiaper("없음");
									} else if (place.get().getDiaper().equals("남여")) {
										place.get().setDiaper("여자");
									}
									placeRepository.save(place.get());
								} 
								else if (r.getContent().equals("여자 화장실 기저귀 교환대가 있음")) {
									if (place.get().getDiaper().equals("없음")) {
										place.get().setDiaper("여자");
									} else if (place.get().getDiaper().equals("남자")) {
										place.get().setDiaper("남여");
									}
									placeRepository.save(place.get());
								} 
								else if (r.getContent().equals("여자 화장실 기저귀 교환대가 없거나 파손됨")) {
									if (place.get().getDiaper().equals("여자")) {
										place.get().setDiaper("없음");
									} 
									else if (place.get().getDiaper().equals("남여")) {
										place.get().setDiaper("남자");
									}
									placeRepository.save(place.get());
								}
							} 
							else if (r.getType().equals("장애인 화장실 정보 수정")) {
								if (r.getContent().equals("남자 장애인 화장실이 있음")) {
									place.get().setDisabled_man("있음");
									placeRepository.save(place.get());
								} 
								else if (r.getContent().equals("남자 장애인 화장실이 없음")) {
									place.get().setDisabled_man("없음");
									placeRepository.save(place.get());
								} 
								else if (r.getContent().equals("여자 장애인 화장실이 있음")) {
									place.get().setDisabled_woman("있음");
									placeRepository.save(place.get());

								} 
								else if (r.getContent().equals("여자 장애인 화장실이 없음")) {
									place.get().setDisabled_woman("없음");
									placeRepository.save(place.get());
								}
							} 
							else if (r.getType().equals("장소명 및 위치")) {
								Opinion o = new Opinion();
								o.setPlaceId(Integer.parseInt(placeId));
								o.setContent(r.getContent());
								opinionRepository.save(o);
							}
							reportRepository.deleteById(r.getId());
							return ResponseEntity.status(400).body("count3");
							
						} 
						else {
							reportRepository.save(r);
							return ResponseEntity.ok("Request successful");
						}
					}
				}
			} 
			reportRepository.save(report);
			return ResponseEntity.ok("Request successful");
		}
		return ResponseEntity.status(400).body("fail");
	}

	@Transactional
	public void reportDelete(int reportId) {
		reportRepository.deleteById(reportId);
	}

	@Transactional
	public ResponseEntity<String> reportClickHeart(int reportId, int placeId, String ip) {
		Optional<Place> place = placeRepository.findById(placeId);
		Optional<Report> reports = reportRepository.findById(reportId);
		System.out.println(reportId);
		Report r = reports.get();
		/*
		if (ip.equals(r.getIp())) {
			return ResponseEntity.status(400).body("ip");
		} else if (ip.equals(r.getIp2())) {
			return ResponseEntity.status(400).body("ip");
		} else if (ip.equals(r.getIp3())) {
			return ResponseEntity.status(400).body("ip");
		}
		System.out.println(ip);
		if (r.getIp2() == null)
			r.setIp2(ip);
		else if (r.getIp3() == null)
			r.setIp3(ip);
			*/
		r.setCount(r.getCount() + 1);

		if (r.getCount() >= 3) {
			if (r.getType().equals("장소 삭제")) {
				placetrashRepository.save(place.get());
				placeRepository.deleteById(place.get().getId());
			} else if (r.getType().equals("비상벨 정보 수정")) {

				if (r.getContent().equals("남자 화장실 비상벨이 있음")) {
					String s = place.get().getEmergency_bell() + "남자";
					place.get().setEmergency_bell(s);
					placeRepository.save(place.get());
				} else if (r.getContent().equals("남자 화장실 비상벨이 없거나 파손됨")) {
					String inputString = place.get().getEmergency_bell();
					String s = inputString.replaceAll("(?i)" + "남자", "");
					place.get().setEmergency_bell(s);
					placeRepository.save(place.get());
				} else if (r.getContent().equals("여자 화장실 비상벨이 있음")) {
					String s = place.get().getEmergency_bell() + "여자";
					place.get().setEmergency_bell(s);
					placeRepository.save(place.get());
				} else if (r.getContent().equals("여자 화장실 비상벨이 없거나 파손됨")) {
					String inputString = place.get().getEmergency_bell();
					String s = inputString.replaceAll("(?i)" + "여자", "");
					place.get().setEmergency_bell(s);
					placeRepository.save(place.get());
				} else if (r.getContent().equals("장애인 화장실 비상벨이 있음")) {
					String s = place.get().getEmergency_bell() + "장애";
					place.get().setEmergency_bell(s);
					placeRepository.save(place.get());
				} else if (r.getContent().equals("장애인 화장실 비상벨이 없거나 파손됨")) {
					String inputString = place.get().getEmergency_bell();
					String s = inputString.replaceAll("(?i)" + "장애", "");
					place.get().setEmergency_bell(s);
					placeRepository.save(place.get());
				}

			} else if (r.getType().equals("기저귀 교환대 정보 수정")) {

				if (r.getContent().equals("남자 화장실 기저귀 교환대가 있음")) {
					if (place.get().getDiaper().equals("없음")) {
						place.get().setDiaper("남자");
					} else if (place.get().getDiaper().equals("여자")) {
						place.get().setDiaper("남여");
					}
					placeRepository.save(place.get());
				} else if (r.getContent().equals("남자 화장실 기저귀 교환대가 없거나 파손됨")) {
					if (place.get().getDiaper().equals("남자")) {
						place.get().setDiaper("없음");
					} else if (place.get().getDiaper().equals("남여")) {
						place.get().setDiaper("여자");
					}
					placeRepository.save(place.get());
				} else if (r.getContent().equals("여자 화장실 기저귀 교환대가 있음")) {
					if (place.get().getDiaper().equals("없음")) {
						place.get().setDiaper("여자");
					} else if (place.get().getDiaper().equals("남자")) {
						place.get().setDiaper("남여");
					}
					placeRepository.save(place.get());
				} else if (r.getContent().equals("여자 화장실 기저귀 교환대가 없거나 파손됨")) {
					if (place.get().getDiaper().equals("여자")) {
						place.get().setDiaper("없음");
					} else if (place.get().getDiaper().equals("남여")) {
						place.get().setDiaper("남자");
					}
					placeRepository.save(place.get());
				}

			} else if (r.getType().equals("장애인 화장실 정보 수정")) {
				if (r.getContent().equals("남자 장애인 화장실이 있음")) {
					place.get().setDisabled_man("있음");
					placeRepository.save(place.get());
				} else if (r.getContent().equals("남자 장애인 화장실이 없음")) {
					place.get().setDisabled_man("없음");
					placeRepository.save(place.get());
				} else if (r.getContent().equals("여자 장애인 화장실이 있음")) {
					place.get().setDisabled_woman("있음");
					placeRepository.save(place.get());

				} else if (r.getContent().equals("여자 장애인 화장실이 없음")) {
					place.get().setDisabled_woman("없음");
					placeRepository.save(place.get());
				}
			} else if (r.getType().equals("장소명 및 위치")) {
				Opinion o = new Opinion();
				o.setPlaceId(placeId);
				o.setContent(r.getContent());
				opinionRepository.save(o);
			}
			reportRepository.deleteById(r.getId());
			return ResponseEntity.status(400).body("count3");
		}
		return ResponseEntity.ok("Request successful");
	}

	@Transactional
	public void DeleteAll() {
		reportRepository.deleteAll();
	}
}
