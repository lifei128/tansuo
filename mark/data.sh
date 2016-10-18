#!/usr/bin/env bash



curl --get --include  'https://api.heweather.com/x3/citylist?search=allchina&key=d4f378687ee34913b3e0388c75b1e9c1'
curl --get --include 'https://api.heweather.com/x3/weather?cityid=CN101010100&key=d4f378687ee34913b3e0388c75b1e9c1'