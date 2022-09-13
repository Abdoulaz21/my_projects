#include "config.hh"

#include <fstream>
#include <iostream>
#include <sstream>

#include "error/parsing-error.hh"
#include "events/events.hh"
#include "events/listener.hh"
#include "events/register.hh"
#include "init/init.hh"
#include "misc/addrinfo/addrinfo.hh"
#include "misc/json.hh"
#include "socket/default-socket.hh"
#include "socket/socket.hh"
#include "socket/ssl-socket.hh"
#include "vhost/dispatcher.hh"
#include "vhost/vhost-factory.hh"

namespace http
{
    static json get_json(std::string &json_file)
    {
        std::ifstream i(json_file);
        json j;
        i >> j;
        i.close();
        return j;
    }

    static void parse_vhost_ip(json &j, VHostConfig &vhconf, unsigned &pos)
    {
        if (j["vhosts"][pos]["ip"].is_null())
            throw ParsingError("ip is required!");
        else if (j["vhosts"][pos]["ip"].is_string() == 0)
            throw ParsingError("ip must be a string!");
        else
            vhconf.vhost_ip = j["vhosts"][pos]["ip"];
    }

    static void parse_vhost_port(json &j, VHostConfig &vhconf, unsigned &pos)
    {
        if (j["vhosts"][pos]["port"].is_null())
            throw ParsingError("port is required!");
        else if (j["vhosts"][pos]["port"].is_number() == 0)
            throw ParsingError("port must be an integer!");
        else
            vhconf.vhost_port = j["vhosts"][pos]["port"];
    }

    static void parse_vhost_server_name(json &j, VHostConfig &vhconf,
                                        unsigned &pos)
    {
        if (j["vhosts"][pos]["server_name"].is_null())
            throw ParsingError("server_name is required!");
        else if (j["vhosts"][pos]["server_name"].is_string() == 0)
            throw ParsingError("server_name must be a string!");
        else
            vhconf.vhost_server_name = j["vhosts"][pos]["server_name"];
    }

    static void parse_vhost_root(json &j, VHostConfig &vhconf, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"].is_null() == 0)
            throw ParsingError("root and proxy_pass can't be both present!");
        if (j["vhosts"][pos]["root"].is_string() == 0)
            throw ParsingError("root must be a string!");
        else
            vhconf.vhost_root = j["vhosts"][pos]["root"];
    }

    static void parse_proxy_pass_ip(json &j, ProxyPass &pp, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"]["ip"].is_null())
            throw ParsingError("ip is required in proxy_pass when "
                               "upstream is not!");
        else if (j["vhosts"][pos]["proxy_pass"]["ip"].is_string() == 0)
            throw ParsingError("ip must be a string!");
        else
            pp.pp_ip = j["vhosts"][pos]["proxy_pass"]["ip"];
    }

    static void parse_proxy_pass_port(json &j, ProxyPass &pp, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"]["port"].is_null())
            throw ParsingError("port is required in proxy_pass "
                               "when upstream is not!");
        else if (j["vhosts"][pos]["proxy_pass"]["port"].is_number() == 0)
            throw ParsingError("port must be an integer!");
        else
            pp.pp_port = j["vhosts"][pos]["proxy_pass"]["port"];
    }

    static void parse_proxy_pass_upstream(json &j, ProxyPass &pp, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"]["upstream"].is_null())
            throw ParsingError("upstream is required in proxy_pass "
                               "when ip/port is not!");
        else if (j["vhosts"][pos]["proxy_pass"]["upstream"].is_string() == 0)
            throw ParsingError("upstream must be a string!");
        else
            pp.pp_upstream = j["vhosts"][pos]["proxy_pass"]["upstream"];
    }

    static void parse_proxy_pass_pp_set_h(json &j, ProxyPass &pp, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"]["proxy_set_header"].is_null() == 0)
        {
            if (j["vhosts"][pos]["proxy_pass"]["proxy_set_header"].is_object()
                == 0)
                throw ParsingError("proxy_set_header must be a string array!");
            auto xx = j["vhosts"][pos]["proxy_pass"]["proxy_set_header"];
            for (json::iterator it = xx.begin(); it != xx.end(); ++it)
            {
                if (it.value().is_string() == 0)
                    throw ParsingError(
                        "proxy_set_header must contain strings only!");
                else
                    pp.pp_proxy_set_h.insert({ it.key(), it.value() });
            }
        }
    }

    static void parse_proxy_pass_pp_rm_h(json &j, ProxyPass &pp, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"]["proxy_remove_header"].is_null()
            == 0)
        {
            if (j["vhosts"][pos]["proxy_pass"]["proxy_remove_header"].is_array()
                == 0)
                throw ParsingError(
                    "proxy_remove_header must be a string array!");
            for (auto xx :
                 j["vhosts"][pos]["proxy_pass"]["proxy_remove_header"])
            {
                if (xx.is_string() == 0)
                    throw ParsingError("proxy_remove_header must "
                                       "contain strings only!");
                else
                    pp.pp_proxy_rm_h.push_back(xx);
            }
        }
    }

    static void parse_proxy_pass_set_h(json &j, ProxyPass &pp, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"]["set_header"].is_null() == 0)
        {
            if (j["vhosts"][pos]["proxy_pass"]["set_header"].is_object() == 0)
                throw ParsingError("set_header must be a string array!");
            auto xx = j["vhosts"][pos]["proxy_pass"]["set_header"];
            for (json::iterator it = xx.begin(); it != xx.end(); ++it)
            {
                if (it.value().is_string() == 0)
                    throw ParsingError("set_header must contain strings only!");
                else
                    pp.pp_set_h.insert({ it.key(), it.value() });
            }
        }
    }

    static void parse_proxy_pass_rm_h(json &j, ProxyPass &pp, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"]["remove_header"].is_null() == 0)
        {
            if (j["vhosts"][pos]["proxy_pass"]["remove_header"].is_array() == 0)
                throw ParsingError("remove_header must be a string array!");
            for (auto xx : j["vhosts"][pos]["proxy_pass"]["remove_header"])
            {
                if (xx.is_string() == 0)
                    throw ParsingError(
                        "remove_header must contain strings only!");
                else
                    pp.pp_rm_h.push_back(xx);
            }
        }
    }

    static void parse_vhost_proxy_pass(json &j, ProxyPass &pp, unsigned &pos)
    {
        if (j["vhosts"][pos]["proxy_pass"].is_null())
            throw ParsingError("root or proxy_pass is required!");
        if (j["vhosts"][pos]["proxy_pass"]["upstream"].is_null())
        {
            parse_proxy_pass_ip(j, pp, pos);
            parse_proxy_pass_port(j, pp, pos);
        }
        else if (j["vhosts"][pos]["proxy_pass"]["ip"].is_null()
                 && j["vhosts"][pos]["proxy_pass"]["port"].is_null())
        {
            parse_proxy_pass_upstream(j, pp, pos);
        }
        else
            throw ParsingError("upstream and ip/port cannot be both "
                               "present in proxy_pass!");

        parse_proxy_pass_pp_set_h(j, pp, pos);
        parse_proxy_pass_pp_rm_h(j, pp, pos);
        parse_proxy_pass_set_h(j, pp, pos);
        parse_proxy_pass_rm_h(j, pp, pos);
    }

    static void parse_vhost_default_file(json &j, VHostConfig &vhconf,
                                         unsigned &pos)
    {
        if (j["vhosts"][pos]["default_file"].is_null() == 0
            and j["vhosts"][pos]["default_file"].is_string() == 0)
            throw ParsingError("default_file must be a string!");
        if (j["vhosts"][pos]["default_file"].is_null() == 0)
            vhconf.vhost_def_file = j["vhosts"][pos]["default_file"];
    }

    static void parse_vhost_ssl(json &j, VHostConfig &vhconf, unsigned &pos)
    {
        if ((!j["vhosts"][pos].contains("ssl_cert")
             and j["vhosts"][pos].contains("ssl_key"))
            or (j["vhosts"][pos].contains("ssl_cert")
                and !j["vhosts"][pos].contains("ssl_key")))
            throw ParsingError("ssl_cert and ssl_key are required together!");
        if (j["vhosts"][pos].contains("ssl_cert")
            and j["vhosts"][pos].contains("ssl_key"))
        {
            // ssl_cert
            if (j["vhosts"][pos]["ssl_cert"].is_string() == 0)
                throw ParsingError("ssl_cert must be a string!");
            else
                vhconf.vhost_ssl_cert = j["vhosts"][pos]["ssl_cert"];

            // ssl_key
            if (j["vhosts"][pos]["ssl_key"].is_string() == 0)
                throw ParsingError("ssl_key must be a string!");
            else
                vhconf.vhost_ssl_key = j["vhosts"][pos]["ssl_key"];
        }
    }

    static void parse_vhost_auth(json &j, VHostConfig &vhconf, unsigned &pos)
    {
        if ((!j["vhosts"][pos].contains("auth_basic")
             and j["vhosts"][pos].contains("auth_basic_users"))
            or (j["vhosts"][pos].contains("auth_basic")
                and !j["vhosts"][pos].contains("auth_basic_users")))
            throw ParsingError(
                "auth_basic and auth_basic_users are required together!");
        if (j["vhosts"][pos].contains("auth_basic")
            and j["vhosts"][pos].contains("auth_basic_users"))
        {
            // auth_basic
            if (j["vhosts"][pos]["auth_basic"].is_string() == 0)
                throw ParsingError("auth_basic must be a string!");
            else
                vhconf.vhost_auth_basic = j["vhosts"][pos]["auth_basic"];

            // auth_basic_users
            if (j["vhosts"][pos]["auth_basic_users"].is_array() == 0)
                throw ParsingError("auth_basic_users must be a string array!");
            else
            {
                for (auto xx : j["vhosts"][pos]["auth_basic_users"])
                {
                    if (xx.is_string() == 0)
                        throw ParsingError(
                            "auth_basic_users must contain strings only!");
                    else
                        vhconf.vhost_auth_basic_users.push_back(xx);
                }
            }
        }
    }

    static void parse_vhost_default_vh(json &j, VHostConfig &vhconf,
                                       unsigned &pos, unsigned &defvh)
    {
        if (j["vhosts"][pos]["default_vhost"].is_null() == 0)
        {
            if (j["vhosts"][pos]["default_vhost"].is_boolean() == 0)
                throw ParsingError("default_vhost must be a boolean!");

            defvh += 1;
            if (defvh > 1)
                throw ParsingError("Only one default_vhost is authorized!");
            vhconf.vhost_def_vh = j["vhosts"][pos]["default_vhost"];
        }
    }

    static void vhost_from_json_file(json &j, ServerConfig &myserver)
    {
        unsigned defvh = 0;

        if (j["vhosts"].is_null())
            throw ParsingError("vhosts are required at the root!");

        unsigned nb_vhosts = j["vhosts"].size();

        for (unsigned pos = 0; pos < nb_vhosts; pos++)
        {
            VHostConfig vhconf;

            parse_vhost_ip(j, vhconf, pos);
            parse_vhost_port(j, vhconf, pos);
            parse_vhost_server_name(j, vhconf, pos);

            // parse root and proxy_pass
            if (j["vhosts"][pos]["root"].is_null())
            {
                ProxyPass pp;
                parse_vhost_proxy_pass(j, pp, pos);
                vhconf.vhost_proxy_pass = pp;
            }
            else
            {
                parse_vhost_root(j, vhconf, pos);
            }

            parse_vhost_default_file(j, vhconf, pos);
            parse_vhost_ssl(j, vhconf, pos);
            parse_vhost_auth(j, vhconf, pos);
            parse_vhost_default_vh(j, vhconf, pos, defvh);

            myserver.vhconfs.push_back(vhconf);
        }
    }

    static void parse_upstream_method(json &j, Upstream &my_ups)
    {
        if (j["upstreams"]["backend"]["method"].is_null())
            throw ParsingError("method is required!");
        else if (j["upstreams"]["backend"]["method"].is_string() == 0)
            throw ParsingError("method must be a string!");
        else
            my_ups.upstream_method = j["upstreams"]["backend"]["method"];
    }

    static void parse_upstream_host(json &j, Host &h, unsigned &x)
    {
        if (j["upstreams"]["backend"]["hosts"][x]["ip"].is_null())
            throw ParsingError("ip is required in hosts!");
        else if (j["upstreams"]["backend"]["hosts"][x]["ip"].is_string() == 0)
            throw ParsingError("ip must be a string!");
        else
            h.h_ip = j["upstreams"]["backend"]["hosts"][x]["ip"];

        if (j["upstreams"]["backend"]["hosts"][x]["port"].is_null())
            throw ParsingError("port is required in hosts!");
        else if (j["upstreams"]["backend"]["hosts"][x]["port"].is_number() == 0)
            throw ParsingError("port must be an integer!");
        else
            h.h_port = j["upstreams"]["backend"]["hosts"][x]["port"];

        if (j["upstreams"]["backend"]["hosts"][x]["health"].is_null()
            && (j["upstreams"]["backend"]["method"] == "fail-robin"
                || j["upstreams"]["backend"]["method"] == "failover"))
            throw ParsingError("health is required for this method!");
        else if (j["upstreams"]["backend"]["hosts"][x]["health"].is_string()
                 == 0)
            throw ParsingError("health must be a string!");
        else
            h.h_health = j["upstreams"]["backend"]["hosts"][x]["health"];

        if (j["upstreams"]["backend"]["hosts"][x]["weight"].is_null() == 0)
        {
            if (j["upstreams"]["backend"]["hosts"][x]["weight"].is_number()
                == 0)
                throw ParsingError("weight must be an integer!");
            else
                h.h_weight = j["upstreams"]["backend"]["hosts"][x]["weight"];
        }
    }

    static void upstream_from_json_file(json &j, ServerConfig &myserver)
    {
        if (j["upstreams"]["backend"].is_null() == 0)
        {
            Upstream my_ups;

            // method
            parse_upstream_method(j, my_ups);

            // hosts
            if (j["upstreams"]["backend"]["hosts"].is_null())
                throw ParsingError("hosts is required!");
            else if (j["upstreams"]["backend"]["hosts"].empty())
                throw ParsingError(
                    "hosts must be an array with at least one element!");
            else
            {
                unsigned nb_h = j["upstreams"]["backend"]["hosts"].size();
                for (unsigned x = 0; x < nb_h; x++)
                {
                    Host h;
                    parse_upstream_host(j, h, x);
                    my_ups.upstream_hosts.push_back(h);
                }
            }
            myserver.ups = my_ups;
        }
    }

    static bool is_wildcard(std::string ip)
    {
        if (ip == "0.0.0.0")
            return true;
        for (auto &it : ip)
        {
            if (it != '0' && it != ':')
                return false;
        }
        return true;
    }

    static void is_differentiable(ServerConfig &myserver)
    {
        unsigned nb_vhosts = myserver.vhconfs.size();
        for (unsigned i = 0; i < nb_vhosts; i++)
        {
            for (unsigned j = 0; j < nb_vhosts; j++)
            {
                if (i == j)
                    continue;
                if (myserver.vhconfs[i].vhost_server_name
                    == myserver.vhconfs[j].vhost_server_name)
                {
                    if (myserver.vhconfs[i].vhost_port
                        == myserver.vhconfs[j].vhost_port)
                    {
                        if ((myserver.vhconfs[i].vhost_ip
                             == myserver.vhconfs[j].vhost_ip)
                            or is_wildcard(myserver.vhconfs[j].vhost_ip)
                            or is_wildcard(myserver.vhconfs[i].vhost_ip))
                            throw ParsingError(
                                "Two vhosts must be differentiable!");
                    }
                }
                if ((myserver.vhconfs[i].vhost_ssl_key != ""
                     && myserver.vhconfs[j].vhost_ssl_key == "")
                    || (myserver.vhconfs[j].vhost_ssl_key != ""
                        && myserver.vhconfs[i].vhost_ssl_key == ""))
                {
                    if (myserver.vhconfs[i].vhost_port
                        == myserver.vhconfs[j].vhost_port)
                    {
                        if ((myserver.vhconfs[i].vhost_ip
                             == myserver.vhconfs[j].vhost_ip)
                            or is_wildcard(myserver.vhconfs[j].vhost_ip)
                            or is_wildcard(myserver.vhconfs[i].vhost_ip))
                            throw ParsingError(
                                "Two vhosts must be differentiable!");
                    }
                }
            }
        }
    }

    static void update_dispatcher_and_loop_listener(ServerConfig sconf)
    {
        for (auto &vhc : sconf.vhconfs)
        {
            try
            {
                auto vh = VHostFactory::Create(vhc);

                if (vhc.vhost_ssl_key != "")
                    vh = VHostFactory::Create_certified(vhc);

                dispatcher.add_vhost(vh);

                shared_socket sockfd =
                    prepare_socket(vhc.vhost_ip, std::to_string(vhc.vhost_port),
                                   vh->ssl_ctx_get());

                event_register.register_event<http::ListenerEW>(sockfd);
            }
            catch (...)
            {
                throw ParsingError("Cannot update dispatcher");
            }
        }
    }

    struct ServerConfig parse_configuration(int argc, char **argv)
    {
        ServerConfig res;
        if (argc == 2)
        {
            res.dryrun = false;
            try
            {
                std::string str = std::string(argv[1]);
                json j = get_json(str);
                vhost_from_json_file(j, res);
                upstream_from_json_file(j, res);
                is_differentiable(res);
            }
            catch (...)
            {
                exit(1);
            }
        }
        else if (argc == 3
                 && (argv[1][0] == '-' && argv[1][1] == 't'
                     && argv[1][2] == '\0'))
        {
            res.dryrun = true;
            try
            {
                std::string str = std::string(argv[2]);
                json j = get_json(str);
                vhost_from_json_file(j, res);
                upstream_from_json_file(j, res);
                is_differentiable(res);
            }
            catch (...)
            {
                exit(1);
            }
        }
        else
            throw ParsingError("Error: Number of args is too high");

        if (res.dryrun)
            exit(0);

        update_dispatcher_and_loop_listener(res);
        return res;
    }

} // namespace http
