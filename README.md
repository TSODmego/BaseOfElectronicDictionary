# BaseOfElectronicDictionary / 电子词典基础

BaseOfElectronicDictionary is an electronic dictionary application based on a client-server architecture.

BaseOfElectronicDictionary 是一个基于客户端-服务器架构的电子词典应用。

## Features / 功能特点

- Server-client model
- Support for multiple client connections
- Simple and easy-to-use command-line interface

- 服务器-客户端模式
- 支持多客户端同时连接
- 简单易用的命令行界面

## Usage Instructions / 使用说明

### Starting the Server / 启动服务器

Use the following command to start the server:

使用以下命令启动服务器：

```
java -jar DictionaryServer.jar <port> <DataSource>
```

Parameters / 参数说明:
- `<port>`: The port number the server will listen on / 服务器监听的端口号
- `<DataSource>`: Path to the dictionary data source file / 词典数据源文件路径

Example / 示例:
```
java -jar DictionaryServer.jar 7674 ./test.txt
```

### Starting the Client / 启动客户端

Use the following command to start the client:

使用以下命令启动客户端：

```
java -jar DictionaryClient.jar <IP address> <port>
```

Parameters / 参数说明:
- `<IP address>`: The IP address of the server / 服务器的IP地址
- `<port>`: The port number of the server / 服务器的端口号

Example / 示例:
```
java -jar DictionaryClient.jar localhost 7674
```

## System Architecture / 系统架构

### User Interaction Diagram / 用户交互图

![User Interaction Diagram / 用户交互图](https://github.com/user-attachments/assets/ee93e0cb-9585-4f3c-a0ed-b9711ca6f31d)

### Interaction Flow Diagram / 交互流程图

![Interaction Flow Diagram / 交互流程图](https://github.com/user-attachments/assets/0886c63a-f809-43dd-8b86-0bb10a4b6420)

## Notes / 注意事项

- Ensure that the server is running before starting the client.
- The port numbers for the client and server must match.
- The data source file should be in a valid dictionary format.

- 确保在启动客户端之前，服务器已经正常运行。
- 客户端和服务器的端口号必须匹配。
- 数据源文件应该是有效的词典格式。



