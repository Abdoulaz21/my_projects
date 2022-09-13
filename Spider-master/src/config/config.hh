/**
 * \file config/config.hh
 * \brief Declaration of ServerConfig and VHostConfig.
 */

#pragma once

#include <map>
#include <string>
#include <vector>

namespace http
{
    struct ProxyPass
    {
        std::string pp_ip;
        int pp_port;
        std::string pp_upstream;
        std::map<std::string, std::string> pp_proxy_set_h = {};
        std::vector<std::string> pp_proxy_rm_h = {};
        std::map<std::string, std::string> pp_set_h = {};
        std::vector<std::string> pp_rm_h = {};
    };

    struct Host
    {
        std::string h_ip;
        int h_port;
        std::string h_health;
        int h_weight = 1;
    };

    /**
     * \struct VHostConfig
     * \brief Value object storing a virtual host configuration.
     *
     * Since each virtual host of the server has its own configuration, a
     * dedicated structure is required to store the information related to
     * each one of them.
     */
    struct VHostConfig
    {
        VHostConfig() = default;
        VHostConfig(const VHostConfig &) = default;
        VHostConfig &operator=(const VHostConfig &) = default;
        VHostConfig(VHostConfig &&) = default;
        VHostConfig &operator=(VHostConfig &&) = default;

        ~VHostConfig() = default;

        std::string vhost_ip;
        int vhost_port;
        std::string vhost_server_name;
        std::string vhost_root = "";
        std::string vhost_def_file = "index.html";
        std::string vhost_ssl_cert = "";
        std::string vhost_ssl_key = "";
        ProxyPass vhost_proxy_pass;
        std::string vhost_auth_basic = "";
        std::vector<std::string> vhost_auth_basic_users = {};
        bool vhost_def_vh = false;
    };

    struct Upstream
    {
        Upstream() = default;
        Upstream(const Upstream &) = default;
        Upstream &operator=(const Upstream &) = default;
        Upstream(Upstream &&) = default;
        Upstream &operator=(Upstream &&) = default;

        ~Upstream() = default;

        std::string upstream_method = "";
        std::vector<Host> upstream_hosts = {};
    };

    /**
     * \struct ServerConfig
     * \brief Value object storing the server configuration.
     *
     * To avoid opening the configuration file each time we need to access the
     * server configuration, a dedicated structure is required to store it.
     */
    struct ServerConfig
    {
        ServerConfig() = default;
        ServerConfig(const ServerConfig &) = default;
        ServerConfig &operator=(const ServerConfig &) = default;
        ServerConfig(ServerConfig &&) = default;
        ServerConfig &operator=(ServerConfig &&) = default;

        ~ServerConfig() = default;

        std::vector<VHostConfig> vhconfs;
        Upstream ups;
        bool dryrun;
    };

    /**
     * \brief Parse the server configuration file.
     *
     * \param path string containing the path to the server configuration
     * file.
     * \return The server configuration.
     */
    struct ServerConfig parse_configuration(int argc, char **argv);

} // namespace http
