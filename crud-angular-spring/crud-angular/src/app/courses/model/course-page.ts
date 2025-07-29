import { Lesson } from "./lesson";
import { Course } from "./course";

export interface CoursePage {
  course: Course[];
  totalElements: number;
  totalPages: number;

}
