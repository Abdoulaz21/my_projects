#include <stdio.h>

void my_rol_crypt(void *data, size_t data_len, const void *key, size_t key_len)
{
    unsigned char *datac = data;
    const unsigned char *keyc = key;
    for (size_t i = 0; i < data_len; i++)
    {
        char val = datac[i] + keyc[i % key_len];
        *(datac + i) = val;
    }
}
