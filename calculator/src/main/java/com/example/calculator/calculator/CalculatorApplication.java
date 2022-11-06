package com.example.calculator.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;


@SpringBootApplication
@RestController
@Service
public class CalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);

	}

	private final AtomicLong counter = new AtomicLong();
	private String badMathMessage = "debes usa el simbolo %2B para la equación +, p.e. 2+2 --> 2%2B2 igual 4";

	@GetMapping(value = "/calculate",
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Answer> calculate(@RequestParam(value = "calculation", defaultValue = "") String calculation,
											@RequestParam(value = "id", defaultValue = "") String id) {
		try {

			if (!calculation.equals(null) && !calculation.equals("")) {
				String newCalc = calculation;
				if (id != null && !id.equals("")) {
					String existingValue = JsonUtil.getString(id);
					newCalc = existingValue + newCalc;
				}
				double result = Math.calculate(newCalc);
				long resultId = counter.incrementAndGet();
				Answer answer = new Answer(resultId, result, newCalc);
				JsonUtil.setString(Long.toString(resultId), Double.toString(result));
				return new ResponseEntity<>(answer, HttpStatus.OK);
			} else {
				String history = JsonUtil.getAll();
				Answer answer = new Answer(0, 0, history);
				return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
			}
		} catch (ScriptException ex) {
			System.out.print(ex);
			Answer answer = new Answer(0, 0, badMathMessage);
			return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			System.out.print(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static String decodeValue(String value) {
		try {
			return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}
}
