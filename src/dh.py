#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  dh.py
#
#  Copyright 2019 Alexander Kolbasov <a-kk1@yandex.ru>
#
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#  MA 02110-1301, USA.
#
#

def getA(p,g,a):
    return (g**a)%p

def getK(p,a,B):
    return (B**a)%p

p,g = 1481,3

a1 = 87
a2 = 51

A1 = getA(p,g,a1)
A2 = getA(p,g,a2)

print(A1)
print(A2)

print(getK(p,a1,A2))
print(getK(p,a2,A1))
