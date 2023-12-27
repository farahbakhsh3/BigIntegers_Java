class BigInteger:
    def __init__(self,value):
        self.__negative = False
        self.string_value = value
        if value[0]=='-':
            self.__negative = True
            value = value.removeprefix('-')
        self.__string_value_no_sign = value
        self.__value = list(map(int,value))
        self.__l_value = len(value)
        
    def __repr__(self):
        return f"BigInteger({self.string_value})"
                     
    @staticmethod
    def __evaluate(self,other,sub=False):
        a:BigInteger = self
        b:BigInteger = other
        if a.__value != [0]:
            while a.__value[0] == 0:
                del a.__value[0]
        if b.__value != [0]:
            while b.__value[0] == 0:
                del b.__value[0]
        n = False
        if a.__l_value == b.__l_value:
            for i in range(a.__l_value):
                if a.__value[i]<b.__value[i]:
                    if sub:
                        n = True
                    a,b = b,a
                    break
                elif a.__value[i]>b.__value[i]:
                    break
        elif b.__l_value > a.__l_value:
            if sub:
                n = True
            a,b = b,a
        
        for i in range(a.__l_value-b.__l_value):
            b.__value.insert(0,0)
            # b.__l_value = len(b.__value)
        if sub:
            return a,b,n
        return a,b
    
    def __compare_no_sign(self, other):
        if self.__l_value > other.__l_value:
            return True
        elif self.__l_value < other.__l_value:
            return False
        elif self.__l_value == other.__l_value:
            for i in range(self.__l_value):
                if self.__value[i]<other.__value[i]:
                    return False
                elif self.__value[i]>other.__value[i]:
                    return True
        return True

    def __gt__(self, other):
        if self.__negative and other.__negative:
            return not self.__compare_no_sign(other)
        elif self.__negative:
            return False
        elif other.__negative:
            return True
        elif self.__value == other.__value:
            return not self.__compare_no_sign(other)
        return  self.__compare_no_sign(other)
    
    def __lt__(self,other):
        if self.__negative and other.__negative:
            if self.__value == other.__value:
                return not self.__compare_no_sign(other)
            return self.__compare_no_sign(other)
        elif self.__negative:
            return True
        elif other.__negative:
            return False
        elif self.__value == other.__value:
            return not self.__compare_no_sign(other)
        return  not self.__compare_no_sign(other)
        
    def __ge__(self,other):
        if self.__negative and other.__negative:
            if self.__value == other.__value:
                return self.__compare_no_sign(other)
            return not self.__compare_no_sign(other)
        elif self.__negative:
            return False
        elif other.__negative:
            return True
        elif self.__value == other.__value:
            return self.__compare_no_sign(other)
        return  self.__compare_no_sign(other)
    
    def __le__(self,other):
        if self.__negative and other.__negative:
            return self.__compare_no_sign(other)
        elif self.__negative:
            return True
        elif other.__negative:
            return False
        elif self.__value == other.__value:
            return self.__compare_no_sign(other)
        return  not self.__compare_no_sign(other)
    
    
    def __add__(self,other):
        a,b = BigInteger.__evaluate(self, other)
        def inner_add(a,b):
            result = []
            carry = 0
            for i in range(a.__l_value-1,-1,-1):
                p = a.__value[i]+b.__value[i]+carry
                if p >= 10:
                    if i == 0:
                        for _ in str(p)[::-1]: 
                            result.insert(0,_)
                    else:
                        carry = int(str(p)[0])
                        result.insert(0,str(p)[1])
                else:
                    carry = 0
                    result.insert(0,str(p))
            result = ''.join(result)
            return result
        
        if a.__negative and b.__negative:
            result = '-'+inner_add(a,b)
        elif a.__negative:
            result = '-'+(BigInteger(a.__string_value_no_sign)-BigInteger(b.__string_value_no_sign)).string_value
        elif b.__negative:
            result = (BigInteger(a.__string_value_no_sign)-BigInteger(b.__string_value_no_sign)).string_value
        else:
            result = inner_add(a,b)
               
        return BigInteger(result)     
            
    def __sub__(self,other):
        
        if (self.string_value == '0') and (other.string_value != '0') and (not other.__negative):
            return BigInteger('-'+other.string_value)
        elif (self.string_value == '0') and (other.__negative):
            return BigInteger(other.__string_value_no_sign)
        elif self.string_value == other.string_value:
            return BigInteger('0')
        
        a,b,n = BigInteger.__evaluate(self,other,True)
        def inner_sub(a,b):
            result = []
            borrow = 0
            for i in range(a.__l_value-1,-1,-1):
                p = a.__value[i]-borrow-b.__value[i]
                if p <0:
                    result.insert(0,str(a.__value[i]-borrow+10-b.__value[i]))
                    borrow = 1
                else:
                    result.insert(0,str(a.__value[i]-borrow-b.__value[i]))
                    borrow = 0
            
            if len(result)>1:        
                while result[0] == '0':
                    del result[0]


            result = ''.join(result)
            return result
        
        if a.__negative and b.__negative:
            if n :
                result = inner_sub(a,b)
            else:
                result = '-'+inner_sub(a,b)
        elif a.__negative:
            if n:
                result = (BigInteger(a.__string_value_no_sign)+BigInteger(b.__string_value_no_sign)).string_value
            else:
                result = '-'+(BigInteger(a.__string_value_no_sign)+BigInteger(b.__string_value_no_sign)).string_value
        elif b.__negative:
            if n:
                result = '-'+(BigInteger(a.__string_value_no_sign)+BigInteger(b.__string_value_no_sign)).string_value
            else:
                result = (BigInteger(a.__string_value_no_sign)+BigInteger(b.__string_value_no_sign)).string_value
        else:
            if n :
                result = '-'+inner_sub(a,b)
            else:
                result = inner_sub(a,b)
        return BigInteger(result)
    
    
    
    def __mul__(self, other):
        a, b = BigInteger.__evaluate(self,other)
        if a.string_value =='0' or b.string_value == '0':
            return BigInteger('0')
        result = []
        carry = 0   
        zero_counter = 0 
        for i in b.__value[::-1]:
            part = []
            if i == 0:
                zero_counter += 1
                continue
            if zero_counter>0:
                part.extend(['0']*zero_counter)
                
            carry = 0
            for j in a.__value[::-1]:
                p = i*j+carry
                if p>9:    
                    part.insert(0,str(p)[1])
                    carry = int(str(p)[0])
                else:
                    part.insert(0,str(p))
                    carry = 0
            if carry != 0:
                part.insert(0,str(carry))
            zero_counter +=1
                
            result.append([''.join(_) for _ in part])

        c = BigInteger('0')
        for i in result:
            c = BigInteger(''.join(i))+c
        
        
        if a.__negative and b.__negative:
            pass
        elif a.__negative or b.__negative:
            c = BigInteger('-'+c.string_value)
                
        return c

    def __truediv__(self, other):
        zeros = self.__l_value-other.__l_value
        other = other*BigInteger('1'+'0'*zeros)
        
        # c = 0
        # while self >= other:
        #     self = self-other
        #     c+=1

        def div(self,other,c,n):
            if n == zeros and zeros !=0:  
                c+='.'
            if n == 15 :
                if zeros== 0:
                    return c[0]+'.'+c[1]
                if zeros > n:
                    return c[0]+'.'+c[1:]+f'e+{zeros}'
                return c
            self = self*BigInteger('10')
            j = 0
            while self >= other:
                self = self-other
                j+=1

            n +=1
            c += str(j)
            return div(self,other,c,n)
            
        if self.__value != [0]:
            r = div(self,other,'',0)            
            return r

        # return str(c)
        
    






a = 83817856891942175892179847812757812784214
b = 812468847921

n1 = BigInteger(str(a))
n2 = BigInteger(str(b))

print(n1/n2)
print(a/b)
# print(a/b)
# print()