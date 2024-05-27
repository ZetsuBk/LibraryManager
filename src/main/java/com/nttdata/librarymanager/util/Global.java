package com.nttdata.librarymanager.util;
import com.nttdata.librarymanager.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Global {
    private Global(){}

    public static final String APIAUTHOR = "/api/authors/";
    public static final String APIBOOK = "/api/books/";
    public static final Logger logger = LoggerFactory.getLogger(Global.class);

    public static final Message message= new Message();
}
