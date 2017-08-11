package nnero.controller;

import nnero.lib.Code;
import nnero.lib.exception.ErrorJSONException;
import nnero.lib.util.JSONResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 2017/8/5 下午5:34 created by NNERO
 */
@RestControllerAdvice(basePackageClasses = ArticleController.class)
public class ArticleErrorController extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ErrorJSONException.class)
    public ResponseEntity<?> handleArticleException(HttpServletRequest request, ErrorJSONException e){
        return ResponseEntity.ok(JSONResult.toJSONString(e.code(),e.getMessage(),null));
    }
}
