package han.swa.herkansing

fun main() {
    // start 4 async nodes
    Node(8000, 9001).start()
    Node(8001).start()
    Node(8002).start()
    Node(8003, 9000).start()

    val e1 = Endpoint(9000, 8003)
        .startMessageReceiver()

    val e2 = Endpoint(9001, 8000)
        .startMessageReceiver()

    // first a message will be sent
    e1.sendMessage()
    Thread.sleep(1000)
    e2.sendMessage()


    // Need to wait for the data to be sent through all nodes to the other endpoint
    Thread.sleep(1000)
}

Enter your message: e1
node 8003 has received message: RelayMessage(message=RelayMessage(message=RelayMessage(message=MessageContent(textMessage=e1), forwardPort=8000), forwardPort=8001), forwardPort=8002).
node 8002 has received message: RelayMessage(message=RelayMessage(message=MessageContent(textMessage=e1), forwardPort=8000), forwardPort=8001).
node 8001 has received message: RelayMessage(message=MessageContent(textMessage=e1), forwardPort=8000).
node 8000 has received message: MessageContent(textMessage=e1).
Endpoint on port 9001 has received message: e1.
