class VariableTable
	def initialize
		@table = {}
	end
	
	def add(var)
		@table[var.name] = var
	end
	
	def get(name)
		@table[name]
	end
	
	def value(name)
		var = @table[name]
		raise "Undefined variable(#{name})!!" if var.nil?
		raise "#{name} is an array!!"unless var.kind_of? Variable
		var.value
	end
	
	def array(name, i)
		var = @table[name]
		raise "Undefined variable(#{name})!!" if var.nil?
		raise "#{name} is NOT an array!!"unless var.kind_of? MyArray
		var.get(i)
	end
	
	def isset(name)
		!@table[name].nil?
	end
end

class MyArray
	attr_accessor :value
	attr_reader :name
	
	def initialize(name)
		@name=name
		@value=[]
		@typesize = nil
	end
	
	def set(i, value)
		if @typesize == nil
			@typesize = 4 if value.class == Fixnum
			@typesize = 8 if value.class == Float
			@typesize = 1 if value.class == String
			#puts "ARRAY typesize = #{@typesize }"
		end
		#puts "SET #{name}[#{i}=#{i/@typesize}] = #{value}"
		@value[i/@typesize]=value
	end
	
	def get(i)
		#puts "GOT #{@value[i/@typesize]} FROM #{@name}[#{i}=#{i/@typesize}]"
		@value[i/@typesize]
	end
end

class Variable
	attr_accessor :value
	attr_reader :name
	def initialize(name, value=nil)
		@name=name
		@value=value
	end
	
	def set(value)
		@value=value
	end
end

class Stack
	def initialize
		@stack = []
	end
	
	def push(a)
		@stack << a
	end
	
	def pop
		@stack.pop
	end
	
	def top
		@stack[-1]
	end
end

class Parser
	def initialize(sources)
		@stack = Stack.new
		@vars = VariableTable.new
		@labels = {}
		@lines = sources
		
		i = 0
		@lines.map! do |str|
			while str =~ /^(L|F)\d+:/
				lable = Regexp.last_match(0)[0...-1]
				@labels[lable] = i
				str = str[lable.length+1..-1]
			end
			i += 1
			str
		end
	end
	
	def lables
		str = "--\n"
		@labels.each do |k,v|
			str+="#{k} = #{v}\n"
		end
		str+"--\n"
	end
	
	def parse
		l = 0
		while l < @lines.size
			line = @lines[l].strip
			index = nil
			l += 1
			next if line == ""

			#puts ">"+line
			
			#  A = EXPR
			if line =~ /^(?<id>\w+)\s*=\s*(?<expr>.*)/ or
			   line =~ /^(?<id>[a-z]\w*)\s*\[\s*(?<i>(\d+|[a-z]\w*))\s*\]\s*=\s*(?<expr>.*)/
				
				id = Regexp.last_match(:id)
				index = Regexp.last_match(:i) if Regexp.last_match.names.include? 'i'
				expr = Regexp.last_match(:expr)
				
				translate(expr);
				#puts expr
				
				value = eval(expr)
				raise "NilExpression: #{expr}" if value.nil?

				index = mkindex(index) if !index.nil?
				
				if !@vars.isset(id)
					if index.nil?
						#puts ">>ADD VAR #{id} = #{value}"
						@vars.add( Variable.new( id, value ) )
					else
						#puts ">>ADD ARRAY #{id} whith #{index} = #{value}"
						@vars.add( MyArray.new(id) ).set(index, value)
					end
				else
					if index.nil?
						#puts "SET #{id} = #{value}"
						@vars.get(id).set(value)
					else
						#puts "SET #{id}[#{index}] = #{value}"
						@vars.get(id).set(index, value)
					end
				end
			end
			
			# IF IFFALSE
			if line =~ /^(?<mode>(if|iffalse)) (?<expr>.*)\s+goto\s+(?<lable>L\d+)/
				expr = Regexp.last_match(:expr)
				lable = Regexp.last_match(:lable)
				mode = Regexp.last_match(:mode)
				
				translate(expr);
				#puts expr
				
				value = eval(expr)
				
				if (mode=='if' and value) or (mode=='iffalse' and !value)
					raise "Unknown lable(#{lable})"if @labels[lable].nil?
					#puts "GOING TO #{lable}"
					l = @labels[lable]
				end
			end
			
			# GOTO
			if line =~ /^goto (?<lable>L\d+)/
				lable = Regexp.last_match(:lable)
				raise "Unknown lable(#{lable})"if @labels[lable].nil?
				#puts "GOING TO #{lable}"
				l = @labels[lable]
			end
			
			# PRINT
			if line =~ /^print\s+(?<id>[a-z]\w*)(\s*\[\s*(?<i>(\d+|[a-z]\w*))\s*\])?/
				id = Regexp.last_match(:id)
				index = Regexp.last_match(:i)
				if index.nil?
					puts "out> #{@vars.value(id)}"
				else
					index = mkindex(index)
					puts "out> #{@vars.array(id, index)}"
				end
			end
		end
	end
	
	private
	
	def translate(expr)
		substrings = []
		c=-1
		expr.gsub!(/(?<id>[a-z]\w*)(\s*\[\s*(?<i>(\d+|[a-z]\w*))\s*\])/) { |s| 
			id = Regexp.last_match(:id)
			i  = Regexp.last_match(:i)
			i.gsub!(/(?<id>[a-z]\w*)/, '@vars.value(\'\k<id>\')')
			substrings <<"@vars.array('#{id}',#{i})"
			"##{c+=1}#"
		}
		expr.gsub!(/(?<id>[a-z]\w*)/, '@vars.value(\'\k<id>\')')
		expr.gsub!(/#(?<i>\d+)#/) { |s| substrings[Regexp.last_match(:i).to_i] }
	end

	def mkindex(index)
		if index =~ /^\d/
			return index.to_i
		else
			return index = @vars.value(index)
		end
	end
end

data = File.new(ARGV[0]).readlines

p = Parser.new(data)
#puts p.lables
p.parse

