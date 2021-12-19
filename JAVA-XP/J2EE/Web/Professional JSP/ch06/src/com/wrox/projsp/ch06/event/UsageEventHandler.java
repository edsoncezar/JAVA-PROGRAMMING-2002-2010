package com.wrox.projsp.ch06.event;

import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

public class UsageEventHandler extends EventHandlerBase {

  private ResourceBundle bundle = ResourceBundle.getBundle("URL");

  protected String getURL () {
    return bundle.getString("USAGE_EVENT");
  }
}
