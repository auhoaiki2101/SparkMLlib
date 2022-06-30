package cloudduggu.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Provides upload, download, view endpoints for spark & client system.
 *
 * @author  Sarvesh Kumar (CloudDuggu.com)
 * @version 1.0
 * @since   2020-08-05
 */

@RestController
public class UploadDownloadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadDownloadController.class);

	static StringBuilder data = new StringBuilder();
	static StringBuilder result = new StringBuilder();

//	download files from client system
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
		Resource resource = null;
		try {
			logger.info("DownloadFile: {}", fileName);
			resource = new ClassPathResource("classpath:data/"+fileName);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

//	upload content into client system
	@PostMapping("/upload")
	public void upload(@RequestParam String type, @RequestParam String text) {
		logger.info("type: "+type+" | text: "+text);

		if(type.equalsIgnoreCase("data")) {
			data.delete(0, data.length());
			data.append(text);
		} else if(type.equalsIgnoreCase("result")) {
			result.delete(0, result.length());
			result.append(text);
		}
	}

//	view data content from memory object
	@GetMapping("/data")
	public String getData() {
		if(data.length() > 1) {
			String temp = data.toString();
			data.delete(0, data.length());
			return temp;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Not Found");
	}

	//	view result content from memory object
	@GetMapping("/result")
	public String getResult() {
		if(result.length() > 1) {
			String temp = result.toString();
			result.delete(0, result.length());
			return temp;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Not Found");
	}

}