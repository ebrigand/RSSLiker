<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>RSS Liker</title>
  <!-- <link href="../resources/css/main.css" rel="stylesheet" type="text/css"/> -->
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  
  <script type="text/javascript">   
    $(document).ready(function() {
  	  //for all the form (each rss story is a 'like' form)
  	  var val = $( "form[id*='likeForm']" );
      //event on submit button    
      $(val).submit(function(event) {
        var buttonElt = $(this).prop('likeButton');
      	var accountNameValue = $(this).prop('accountName').value;
      	var uriWithoutSpecialCharsValue = $(this).prop('uriWithoutSpecialChars').value;
      	var isLikeElt = $(this).prop('isLike');
      	//Change the like value (inverse the value)
      	var isNewLikeValue = (isLikeElt.value == "true") ? false : true;
      	var likeCountElt = $(this).prop('likeCount');
          //Create the JSON of HomeBeanView
      	var json = { "likeCount" : likeCountElt.value, like : {"accountName" : accountNameValue, "uriWithoutSpecialChars" : uriWithoutSpecialCharsValue, "isLike": isNewLikeValue }};
          $.ajax({
            url: $(val).attr("action"),
            data: JSON.stringify(json),
            type: "POST",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(homeBeanView) {
          	  //Change the value of the submit button (if the button was 'Like' it becomes 'UnLike' and the opposite)
                if(homeBeanView.like.isLike){
              	  buttonElt.value = "UnLike";
                }else{
              	  buttonElt.value = "Like"; 
                }
          	  //Change the value of the input hidden value with id 'isLike', update with the new value (coming from the ajax response)
                isLikeElt.value = homeBeanView.like.isLike;
                //Change the value of the input text value with id 'likeCount', update with the new value (coming from the ajax response)
          	  likeCountElt.value = homeBeanView.likeCount;
            }
          
        });
        event.preventDefault();
       });     
    });
  </script>
  
  </head>
  <body>
    <div id="container">
      <h1 style="text-align: center">RSS Liker</h1>
      <table border="0px" cellpadding="0" cellspacing="20">
        <thead>
          <tr>
            <th>RSS: title and content</th><th>Like button / Total likes</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="homeBeanView" items="${homeBeanViews}">
            <tr>
              <td style="width: 500px">
                <b>${homeBeanView.syndEntry.title}</b><br />
                ${homeBeanView.syndEntry.description.value}<br />
              </td>
              <td valign="middle">
                <form id="likeForm${homeBeanView.like.uriWithoutSpecialChars}" method="post" action="${pageContext.request.contextPath}/rss/saveOrUpdate/${homeBeanView.like.uriWithoutSpecialChars}.json">
                    <input type="hidden" id="accountName" name="accountName" type="text" value="${homeBeanView.like.accountName}"/>
                    <input type="hidden" id="uriWithoutSpecialChars" name="uriWithoutSpecialChars" type="text" value="${homeBeanView.like.uriWithoutSpecialChars}"/>
                    <input type="hidden" id="isLike" name="isLike" type="text" value="${homeBeanView.like.isLike}"/>
                    <input id="likeButton" style="width: 80px" type="submit" value="${homeBeanView.like.isLike ? 'UnLike' : 'Like'}" />
                    <img src="img/spacer.gif" alt=" " width="20" height="1" /><input type="text" style="border: none; width: 10px" readonly="readonly" id="likeCount" value="${homeBeanView.likeCount}" /> likes
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </body>
</html>