var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/testDB');
var db = mongoose.connection;
db.on('error', function(){
	console.log('Connection Failed!');
});

db.once('open', function(){
	console.log('Connected!');
});

var student = mongoose.Schema({
	    name : 'string',
	    address : 'string',
	    age : 'number'
});

var Student = mongoose.model('Schema', student);

var newStudent = new Student({name:'Hong Gil Dong', address:'서울시 강남구 논현동', age:'22'});

newStudent.save(function(error, data){
	    if(error){
		            console.log(error);
		        }else{
				        console.log('Saved!')
				    }
});

Student.find(function(error, students){
	    console.log('--- Read all ---');
	    if(error){
		            console.log(error);
		        }else{
				        console.log(students);
				    }
})

Student.findOne({_id:'585b777f7e2315063457e4ac'}, function(error,student){
	    console.log('--- Read one ---');
	    if(error){
		            console.log(error);
		        }else{
				        console.log(student);
				    }
});

Student.findById({_id:'585b777f7e2315063457e4ac'}, function(error,student){
	    console.log('--- Update(PUT) ---');
	    if(error){
		            console.log(error);
		        }else{
				        student.name = '--modified--';
				        student.save(function(error,modified_student){
						            if(error){
								                    console.log(error);
								                }else{
											                console.log(modified_student);
											            }
						        });
				    }
});

Student.remove({_id:'585b7c4371110029b0f584a2'}, function(error,output){
	    console.log('--- Delete ---');
	    if(error){
		            console.log(error);
		        }
	console.log('--- deleted ---');
});
