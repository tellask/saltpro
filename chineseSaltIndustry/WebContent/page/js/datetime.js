//判断日期格式是否正确
function inputdatetime(objectname)
{
var oInput=document.all(objectname);
var range=document.all(objectname).createTextRange();//为元素创建一个 TextRange 对象。
var selrange = document.selection.createRange();//从当前文本选中区中创建 TextRange 对象
var startpos = 0,endpos = 0;
var oLeft;
var oRight;
var oLength;
var pos;
var cursorpos=0;//光标下一个相对位置
var cursorpos1=0;//光标结束位置
var keytext;
var curtext=range.text;//当前input框文本
var charbuff;//暂存字符
while(selrange.compareEndPoints("StartToStart",range)>0)//比较 TextRange 对象的结束点和其它范围的结束点。
{
selrange.moveStart("character",-1);//更改范围的开始位置。Moves one or more characters
startpos ++;
}
oLength=curtext.length;

if(startpos>18)//光标位置超出范围
   {    
    if(event.keyCode==8)//后退键特殊情况
        { oLeft=curtext.substring(0,startpos);oRight="0";
          oInput.value=oLeft+oRight; 
          range.moveStart("character",19);  
          range.collapse(true);//将插入点移动到当前范围的开始或结尾。  
          range.select();
          } //将当前选中区置为当前对象。}
    else if(event.keyCode==46||event.keyCode==39||event.keyCode==40)
          {          
          range.moveStart("character",19);  
          range.collapse(true);
          range.select();
        }
    else if(event.keyCode==37)//向前键 
          {          
          range.moveStart("character",19);  
          range.collapse(true);  
          range.select();
        }
    else if(event.keyCode==38)//向上键 
          {
          range.moveStart("character",18);  
          range.collapse(true);  
          range.select();
        }
    else{
         oInput.value = curtext.substring(0,18);
         range.moveStart("character",18);
          range.moveEnd("character",19);
         range.collapse(true);  
          range.select(); //将当前选中区置为当前对象。
         }
    }
else if(event.keyCode>47&&event.keyCode<58)//按键是数字
   {
    if(startpos==0)
        {oLeft="";oRight=curtext.substring(1,oLength);cursorpos=0;cursorpos1=1;}   
     else if(startpos==4||startpos==7)
        {oLeft=curtext.substring(0,startpos)+"-";oRight=curtext.substring(startpos+2,oLength);cursorpos=1;cursorpos1=2;}
     else if(startpos==10)
        {oLeft=curtext.substring(0,startpos)+" ";oRight=curtext.substring(startpos+2,oLength);cursorpos=1;cursorpos1=2;}
     else if(startpos==13||startpos==16)
       {oLeft=curtext.substring(0,startpos)+":";oRight=curtext.substring(startpos+2,oLength);cursorpos=1;cursorpos1=2;}
     else if(startpos>0&&startpos<19)
       {oLeft=curtext.substring(0,startpos);oRight=curtext.substring(startpos+1,oLength);cursorpos=0;cursorpos1=1;}
        oInput.value = oLeft+oRight; 
       range.moveStart("character",startpos+cursorpos);
       range.moveEnd("character",startpos+cursorpos1);
       range.collapse(true);  
       range.select();        
  }
else if(event.keyCode==8)//后退按键时，当前一个字符为"-"或" "或":"时不变，否则替换为"0"
  {
     if(startpos==0)
        {oLeft="";oRight=curtext.substring(startpos,oLength);}
      else if(startpos==14||startpos==17)
        {oLeft=curtext.substring(0,startpos);oRight=":"+curtext.substring(startpos,oLength);}
      else if(startpos==11)
        {oLeft=curtext.substring(0,startpos);oRight=" "+curtext.substring(startpos,oLength);}
      else if(startpos==5||startpos==8)
        {oLeft=curtext.substring(0,startpos);oRight="-"+curtext.substring(startpos,oLength);}
      else if(startpos==1||startpos==2||startpos==3||startpos==4||startpos==6||startpos==7||startpos==9||startpos==10||startpos==12||startpos==13||startpos==15||startpos==16||startpos==18)
        {oLeft=curtext.substring(0,startpos);oRight="0"+curtext.substring(startpos,oLength);}
      else 
        {oLeft=curtext.substring(0,startpos);oRight=curtext.substring(startpos-1,oLength);}
     oInput.value = oLeft+oRight;
     range.moveStart("character",startpos);  
     range.collapse(true);  
     range.select();  
   }
else if(event.keyCode==46)//delete按键,当后一个字符为"-"或" "或":"时不变，否则替换为"0"
  { 
      if(startpos==0)
        {oLeft="0";oRight=curtext.substring(startpos,oLength);}
      else if(startpos==13||startpos==16)
        {oLeft=curtext.substring(0,startpos)+":";oRight=curtext.substring(startpos,oLength);}
      else if(startpos==10)
        {oLeft=curtext.substring(0,startpos)+" ";oRight=curtext.substring(startpos,oLength);}
      else if(startpos==4||startpos==7)
        {oLeft=curtext.substring(0,startpos)+"-";oRight=curtext.substring(startpos,oLength);}
      else if(startpos==1||startpos==2||startpos==3||startpos==5||startpos==6||startpos==8||startpos==9||startpos==11||startpos==12||startpos==14||startpos==15||startpos==17||startpos==18)
        {oLeft=curtext.substring(0,startpos)+"0";oRight=curtext.substring(startpos,oLength);}
      else 
        {oLeft=curtext.substring(0,startpos+1);oRight=curtext.substring(startpos,oLength);}  
    oInput.value = oLeft+oRight;
    range.moveStart("character",startpos+1);  
    range.collapse(true);  
    range.select();  
   }
else if(event.keyCode==38)//向上键 
   {
    range.moveStart("character",startpos-1);  
    range.collapse(true);  
    range.select();
   }
else if(event.keyCode==40)//向下键
   {
    range.moveStart("character",startpos+1);  
    range.collapse(true);  
    range.select(); 
    }
else //其他按键不做反应
  {
    oLeft=curtext.substring(0,startpos);
    oRight=curtext.substring(startpos,oLength);
    oInput.value = oLeft+oRight;
    range.moveStart("character",startpos);  
    range.collapse(true);  
    range.select();  
  }
}
function changedatetime(objectname)//将日期格式规范化成 yyyy-mm-dd hh-mm-ss
{ 
  var textbuff;
  var year,month,day,minute,hour,second;
  var curtext=document.all(objectname).value; 
  year=curtext.substring(0,curtext.indexOf("-"));
  curtext=curtext.substring(curtext.indexOf("-")+1,curtext.length)
  month=curtext.substring(0,curtext.indexOf("-"));
  if(month.length<2){month="0"+month;}
  curtext=curtext.substring(curtext.indexOf("-")+1,curtext.length)
  day=curtext.substring(0,curtext.indexOf(" "));
  if(day.length<2){day="0"+day;}
  curtext=curtext.substring(curtext.indexOf(" ")+1,curtext.length)
  hour=curtext.substring(0,curtext.indexOf(":"));
  if(hour.length<2){hour="0"+hour;}
  curtext=curtext.substring(curtext.indexOf(":")+1,curtext.length)
  minute=curtext.substring(0,curtext.indexOf(":"));
  if(minute.length<2){minute="0"+minute;}
  curtext=curtext.substring(curtext.indexOf(":")+1,curtext.length)
  second=curtext.substring(0,curtext.length);
   if(second.length<2){second="0"+second;}
   return ( year+"-"+month+"-"+day+" "+minute+":"+hour+":"+second)
}
function isDate(checktext)//日期时间检查
{  
  var   datetime;  
  var   year,month,day,hour,munite,second;  
  var   gone,gtwo,gthree,gfour,gfive;  
     datetime=document.all(checktext).value;
      year=datetime.substring(0,4);  
      gone=datetime.substring(4,5);  
      month=datetime.substring(5,7);  
      gtwo=datetime.substring(7,8);  
      day=datetime.substring(8,10); 
      gthree=datetime.substring(10,11);
      hour=datetime.substring(11,13);
      gfour=datetime.substring(13,14);
      munite=datetime.substring(14,16);
      gfive=datetime.substring(16,17);
      second=datetime.substring(17,19);
      if((gone=="-")&&(gtwo=="-")&&(gthree==" ")&&(gfour==":")&&(gfive==":"))
        {  
          if(month<1||month>12){alert("月份必须在01和12之间!");return false;}    
          if(day<1||day>31){alert("日期必须在01和31之间!");return false;}
          else{  
                 if(month==2)
                  {      
                   if((year%4)==0&&day>29){alert("二月份日期必须在01到29之间!");return false;}                
                   if((year%4)>0&&day>28){alert("二月份日期必须在01到28之间!");return false;}    
                  }    
                 if((month==4||month==6||month==9||month==11)&&(day>30)){alert(" 在四，六，九，十一月份   /n日期必须在01到30之间!");return   false;}    
               }
          if(hour>23){alert("小时必须在00和24之间!");return false;}
          if(munite>59){alert("分钟必须在00和60之间!");return false;}
          if(second>59){alert("秒必须在00和60之间!");return false;}
         }
      else
        {  
           alert("请输入日期!格式为(yyyy-mm-dd hh-mm-ss)   /n例(2001-01-01 01:00:00)");
           return   false;  
         }  
return true;   
}