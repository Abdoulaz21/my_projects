#include "vhost-factory.hh"

#include "vhost.hh"

namespace http
{
    shared_vhost VHostFactory::Create(VHostConfig config)
    {
        VHostStaticFile *vhost = new VHostStaticFile(config);
        return std::shared_ptr<VHostStaticFile>(vhost);
    }

    shared_vhost VHostFactory::Create_certified(VHostConfig config)
    {
        VHostStaticFile *vhost = new VHostStaticFile(config);
        vhost->certified();
        return std::shared_ptr<VHostStaticFile>(vhost);
    }
} // namespace http
