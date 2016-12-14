/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safeway.html5andservlet31;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author scher20
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"}, asyncSupported=true)
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext context = req.startAsync();
        // set up async listener
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                event.getSuppliedResponse().getOutputStream().print("Complete");

            }

            @Override
            public void onError(AsyncEvent event) {
                System.out.println(event.getThrowable());
            }

            @Override
            public void onStartAsync(AsyncEvent event) {
            }

            @Override
            public void onTimeout(AsyncEvent event) {
                System.out.println("my asyncListener.onTimeout");
            }
        });
        ServletInputStream input = req.getInputStream();
        ReadListener readListener = new ReadListenerImpl(input, resp, context);
        input.setReadListener(readListener);
    }
    }

    