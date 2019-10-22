package com.monolithic.springmvc.controller;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class MonolithicController {

	String message = "Welcome to Monolithic Spring MVC Application!!!";

	@RequestMapping("/welcome")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "JavaIsOcean") String name) {
		ModelAndView mv = new ModelAndView("welcome");
		// jvm micrometer registry code needs to be refactored and should not be called for every request.
		MeterRegistry registry = jvmMicroMeterJmxMonitoring();
		new JvmMemoryMetrics().bindTo(registry);
		mv.addObject("name", name);
		mv.addObject("message", message);
		return mv;
	}



	public  MeterRegistry jvmMicroMeterJmxMonitoring() {
		return new JmxMeterRegistry(JmxConfig.DEFAULT, Clock.SYSTEM, HierarchicalNameMapper.DEFAULT);
}
}
