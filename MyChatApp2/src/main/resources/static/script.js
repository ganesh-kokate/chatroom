var stompClient=null
var globalUsername;


   function sendMessage(){

    let jsonOb={
        name: globalUsername,
        content:$("#message-value").val()
    }

    stompClient.send("/app/message",{},JSON.stringify(jsonOb));



   }



function connect()
{
	   //initializes a new WebSocket connection using SockJS to the server endpoint "/server1"
        let socket=new SockJS("/server1")

		//create a STOMP client over the WebSocket connection provided by SockJS.
        stompClient=Stomp.over(socket)

		
		//defination of connect function
        stompClient.connect({},function(frame){
			
			//access username from session and stored it
			globalUsername = frame.headers["user-name"];
            console.log("Connected : "+frame)



                //subscribe to topic and it will brodcast msgs and everyone can see it 
                stompClient.subscribe("/topic/return-to",function(response){

                        showMessage(JSON.parse(response.body))

                })



        })

}


//to display msgs
 function showMessage(message)
 {

	//created one html table and accessed it and add new table row when new msg appears
    $("#message-container-table").prepend(`<tr><td><b>${message.name} :</b> ${message.content}</td></tr>`)

 }


$(document).ready(() => {
	
    connect();
  
});


 