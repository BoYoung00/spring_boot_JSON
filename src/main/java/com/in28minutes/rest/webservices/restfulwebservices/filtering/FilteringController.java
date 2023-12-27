package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

// 전송하고 싶은 객체만 필터링
@RestController // JSON 또는 XML 전송할 때
public class FilteringController {
    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        SomeBean someBean = new SomeBean("Value1", "Value2", "Value3");
        // MappingJacksonValue : JSON 응답의 일부를 동적 필터링
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        // 필터 "SomeBeanFilter"를 필터 클래스에 정의해야함
        // field1, field3만 나오게 필터링
        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter
                        .filterOutAllExcept("field1", "field3");
        FilterProvider filters
                = new SimpleFilterProvider()
                .addFilter("SomeBeanFilter", filter);

        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {
        List<SomeBean> list = Arrays.asList(
                new SomeBean("Value1", "Value2", "Value3"),
                new SomeBean("Value4", "Value5", "Value6"));
        // MappingJacksonValue : JSON 응답의 일부를 동적 필터링
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
        // 필터 "SomeBeanFilter"를 필터 클래스에 정의해야함
        // field1, field3만 나오게 필터링
        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter
                        .filterOutAllExcept("field2", "field3");
        FilterProvider filters
                = new SimpleFilterProvider()
                .addFilter("SomeBeanFilter", filter);

        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
}

