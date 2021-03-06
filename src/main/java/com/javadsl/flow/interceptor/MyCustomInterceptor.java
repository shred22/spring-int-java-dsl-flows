package com.javadsl.flow.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyCustomInterceptor extends HandlerInterceptorAdapter {


  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object object) {
    System.out.println("In preHandle() we are Intercepting the Request");
    System.out.println("____________________________________________");
    String requestURI = request.getRequestURI();
    System.out.println("RequestURI::" + requestURI +
        " || INTEGRATION SERVICE");
    System.out.println("____________________________________________");
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response,
      Object object, ModelAndView model) {
    System.out.println("_________________________________________");
    System.out.println("In postHandle() request processing "
        + "completed by @RestController");
    System.out.println("_________________________________________");
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object object, Exception arg3) {
    System.out.println("________________________________________");
    System.out.println("In afterCompletion().. Request Completed");
    System.out.println("________________________________________");
  }

}