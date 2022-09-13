/**
 * \file vhost/vhost-static-file.hh
 * \brief VHostStaticFile declaration.
 */

#include "vhost/vhost-static-file.hh"

namespace http
{
    VHostStaticFile::VHostStaticFile(const VHostConfig &config)
        : VHost(config)
    {}

    static bool file_exist(const std::string name)
    {
        return (access(name.c_str(), F_OK) != -1);
    }

    static bool checkMethod(const std::string method)
    {
        return method == "GET" || method == "POST" || method == "HEAD";
    }

    static void readDefFile(std::ifstream &my_file,
                            std::shared_ptr<Connection> &conn)
    {
        my_file.seekg(0, my_file.end);
        conn->resp.len = my_file.tellg();
        my_file.seekg(0, my_file.beg);
        if (conn->resp.len >= 900000000000000000)
        {
            conn->resp.len = 0;
            return;
        }
        std::string content = "";
        content.resize(conn->resp.len);
        my_file.read(content.data(), conn->resp.len);
        conn->resp.message = content;
    }

    static void readFile(std::ifstream &my_file,
                         std::shared_ptr<Connection> &conn,
                         std::string filename, std::string def_file)
    {
        my_file.seekg(0, my_file.end);
        conn->resp.len = my_file.tellg();
        my_file.seekg(0, my_file.beg);
        if (conn->resp.len >= 900000000000000000)
        {
            std::string new_uri = filename + def_file;
            std::ifstream my_newfile(new_uri);
            readDefFile(my_newfile, conn);
            my_newfile.close();
            return;
        }
        std::string content = "";
        content.resize(conn->resp.len);
        my_file.read(content.data(), conn->resp.len);
        conn->resp.message = content;
    }

    void VHostStaticFile::respond(Request &req,
                                  std::shared_ptr<Connection> conn)
    {
        if (!checkMethod(req.method))
        {
            conn->resp = Response(METHOD_NOT_ALLOWED);
            return;
        }
        std::string filePath = conf_.vhost_root + req.uri;
        if (!file_exist(filePath))
        {
            conn->resp = Response(NOT_FOUND);
            return;
        }
        std::ifstream my_file(filePath);
        if (!my_file.good())
        {
            conn->resp = Response(FORBIDDEN);
            return;
        }
        if (req.protocol_vers != "HTTP/1.1")
        {
            conn->resp = Response(UPGRADE_REQUIRED);
            return;
        }
        conn->resp = Response(OK);
        readFile(my_file, conn, filePath, conf_.vhost_def_file);
        my_file.close();
    }
} // namespace http
