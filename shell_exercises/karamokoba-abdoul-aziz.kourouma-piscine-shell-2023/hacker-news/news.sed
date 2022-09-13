#s/^\(.*href=".*\).*$/\1/p
s/^.*<a href="\(.*\)" class="storylink">\(.*\)<\/a><span.*/**\2**\n\1\n/p
