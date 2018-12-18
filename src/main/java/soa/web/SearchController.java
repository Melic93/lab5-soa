package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.Status;
import java.util.*;

@Controller
public class SearchController {

  private final ProducerTemplate producerTemplate;

  @Autowired
  public SearchController(ProducerTemplate producerTemplate) {
    this.producerTemplate = producerTemplate;
  }

  @RequestMapping("/")
  public String index() {
    return "index";
  }


  @RequestMapping(value = "/search")
  @ResponseBody
  public Object search(@RequestParam(value="q", required=false, defaultValue="write something cool") String q, 
                       @RequestParam(value="max", required=false, defaultValue="10") int max,
                       @RequestParam(value="lang", required=false, defaultValue="en") String lang) {
    
    Map<String, Object> headers = new HashMap<>();

    headers.put("CamelTwitterKeywords", q);
    headers.put("CamelTwitterCount", max);
    headers.put("CamelTwitterSearchLanguage", lang);

    return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
  }
}